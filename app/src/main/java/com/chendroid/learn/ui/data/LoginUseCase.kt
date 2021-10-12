package com.chendroid.learn.ui.data

import com.chendroid.learn.api.ApiServiceHelper
import com.chendroid.learn.bean.LoginResponse
import com.chendroid.learn.core.ResponseResult
import com.chendroid.learn.ui.data.source.LoginDataSource

/**
 * @author      : zhaochen
 * @date        : 2021/9/1
 * @description : 登陆
 */
class LoginUseCase {

    private val loginDataSource: LoginDataSource by lazy {
        LoginDataSource(ApiServiceHelper.newWanService)
    }

    suspend fun loginAccount(username: String, password: String): ResponseResult<LoginResponse> {
        val result = loginDataSource.loginIn(username, password)
        return result
    }

    suspend fun logoutAccount() = loginDataSource.logout()

}