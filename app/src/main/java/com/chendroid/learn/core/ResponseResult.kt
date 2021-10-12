package com.chendroid.learn.core

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description :网络接口返回的结果
 */
//密封，保证在包内被调用
sealed class ResponseResult<out T : Any> {

    /**
     * 请求成功的数据类
     */
    data class Success<out T : Any>(val data: T) : ResponseResult<T>()

    /**
     * 请求失败的数据类
     */
    data class Error(val exception: Exception) : ResponseResult<Nothing>()


    override fun toString(): String {
        return when (this) {
            // todo 为什么加 <*>
            is Success<*> -> "Success 请求成功 data=$data"
            is Error -> "Error 请求失败 exception=$exception"
        }
    }


}