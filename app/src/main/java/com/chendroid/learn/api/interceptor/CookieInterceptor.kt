package com.chendroid.learn.api.interceptor

import com.chendroid.learn.api.ApiServiceHelper
import com.chendroid.learn.core.PreferenceHelper
import com.chendroid.learn.util.DebugLog
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @intro cookie 拦截器， 保存和清除 cookie
 * @author zhaochen@ZhiHu Inc.
 * @since 2020/4/16
 */
class CookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url().toString()
        val domain = request.url().host()

        // set-cookie maybe has multi, login to save cookie, 如果请求中包含用户登陆信息，则缓存用户的 cookie 信息
        if ((requestUrl.contains(ApiServiceHelper.SAVE_USER_LOGIN_KEY) || requestUrl.contains(
                ApiServiceHelper.SAVE_USER_REGISTER_KEY
            ))
            && !response.headers(ApiServiceHelper.SET_COOKIE_KEY).isEmpty()
        ) {
            DebugLog.d("zc_test", "保存 cookie, response is $response")

            val cookies = response.headers(ApiServiceHelper.SET_COOKIE_KEY)
            val cookie = encodeCookie(cookies)
            saveCookie(requestUrl, domain, cookie)
        }

        DebugLog.d("zc_test", "SaveCookieInterceptor request headers is ${response.headers()}")
        if (requestUrl.contains(ApiServiceHelper.LOGOUT_USER_KEY) && response.isSuccessful) {
            DebugLog.d("zc_test", "清除 cookie response is $response")
            // 需要清除 cookie
            clearCookie(requestUrl, domain)
        }

        return response
    }

    /**
     * save cookie to SharePreferences
     */
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    private fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        PreferenceHelper.setValue("account_sp", url, cookies)
        domain ?: return
        PreferenceHelper.setValue("account_sp", domain, cookies)
    }

    private fun clearCookie(url: String, domain: String) {
        PreferenceHelper.setValue("account_sp", url, "")
        PreferenceHelper.setValue("account_sp", domain, "")
    }


    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
            .map { cookie ->
                cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
            .forEach {
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }

        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        return sb.toString()
    }

}