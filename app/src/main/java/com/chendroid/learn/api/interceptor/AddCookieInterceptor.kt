package com.chendroid.learn.api.interceptor

import com.chendroid.learn.api.ApiServiceHelper
import com.chendroid.learn.core.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @intro 为网络请求添加 cookie 拦截器
 * @author zhaochen@ZhiHu Inc.
 * @since 2020/4/16
 */
class AddCookieInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // 如果当前请求中，没有包含用户信息，则手动添加上用户 cookie 信息

        val request = chain.request()
        val builder = request.newBuilder()
        // 查看其域名是否
        val domain = request.url().host()

        // 为请求添加上 cookie
        if (domain.isNotEmpty()) {
            val spCookie: String  = PreferenceHelper.getValue("account_sp", domain, "")

            if (spCookie.isNotEmpty()) {
                builder.addHeader(ApiServiceHelper.COOKIE_NAME, spCookie)
            }
        }

        return chain.proceed(builder.build())
    }
}