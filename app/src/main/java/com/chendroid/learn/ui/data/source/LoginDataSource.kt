package com.chendroid.learn.ui.data.source

import com.chendroid.learn.api.NewWanService
import com.chendroid.learn.bean.LoginResponse
import com.chendroid.learn.bean.LogoutResponse
import com.chendroid.learn.core.ResponseResult

/**
 * @author      : zhaochen
 * @date        : 2021/9/1
 * @description : 登陆有关 data source
 */
class LoginDataSource(private val newWanService: NewWanService) {


    suspend fun loginIn(username: String, password: String): ResponseResult<LoginResponse> {
        val loginResult = newWanService.loginAccount(username, password)

        if (loginResult.isSuccessful) {
            loginResult.body()?.data?.run {
                return ResponseResult.Success(loginResult.body()!!)
            }
        }

        return ResponseResult.Error(Exception("登陆账号失败 error code ${loginResult.code()} error body is ${loginResult.errorBody()}"))
    }

    /**
     * 退出登陆
     */
    suspend fun logout(): ResponseResult<LogoutResponse> {
        val logoutResult = newWanService.logoutAccount()
        if (logoutResult.isSuccessful) {
            return ResponseResult.Success(logoutResult.body()!!)
        }
        return ResponseResult.Error(Exception("登陆账号失败 error code ${logoutResult.code()} error body is ${logoutResult.errorBody()}"))

    }


}