package com.chendroid.learn.bean

/**
 * @author      : zhaochen
 * @date        : 2021/9/2
 * @description : 退出登陆数据
 */
data class LogoutResponse(
    var errorCode: Int,
    var errorMsg: String,
    var data: LogoutData
) {
    data class LogoutData(val tag: String)
}