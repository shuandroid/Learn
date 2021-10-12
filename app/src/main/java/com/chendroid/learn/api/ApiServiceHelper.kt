package com.chendroid.learn.api

import com.chendroid.learn.api.interceptor.AddCookieInterceptor
import com.chendroid.learn.api.interceptor.CookieInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author      : zhaochen
 * @date        : 2021/8/24
 * @description : 创建网络请求帮助类
 */
object ApiServiceHelper {

    private const val TAG = "ApiServiceHelper"
    private const val CONTENT_PRE = "OkHttp: "
    const val SAVE_USER_LOGIN_KEY = "user/login"
    const val SAVE_USER_REGISTER_KEY = "user/register"
    const val LOGOUT_USER_KEY = "user/logout"
    const val SET_COOKIE_KEY = "set-cookie"
    const val COOKIE_NAME = "Cookie"
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 10L

    private const val REQUEST_BASE_URL = "https://www.wanandroid.com/"

    // NewWanService 的网络接口实现类
    val newWanService = getService(REQUEST_BASE_URL, NewWanService::class.java)

    private fun create(url:String): Retrofit {
        // 创建
        val okHttpClientBuilder = OkHttpClient().newBuilder().apply {
            // 基础配置
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            // 添加拦截器，获取 cookie，并缓存
            addInterceptor(CookieInterceptor())
            // 添加 cookie
            addInterceptor(AddCookieInterceptor())

            // 添加网络拦截， 用于 chrome 的 inspect
            addNetworkInterceptor(StethoInterceptor())
        }

        // 配置 retrofit 信息
        val retrofitBuilder = Retrofit.Builder().apply {
            baseUrl(url)
            client(okHttpClientBuilder.build())
            addConverterFactory(GsonConverterFactory.create())
        }

        return retrofitBuilder.build()
    }

    /**
     * 根据 service 获取对应的 网络接口实现类
     */
    private fun <T> getService(url: String, service: Class<T>) : T {
        return create(url).create(service)
    }

}