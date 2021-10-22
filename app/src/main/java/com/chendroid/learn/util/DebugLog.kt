package com.chendroid.learn.util

import android.util.Log
import com.chendroid.learn.BuildConfig

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description : 用来打印 log
 */
object DebugLog {

    private const val TAG = "zc_test"
    private val DEBUG = BuildConfig.DEBUG

    /**
     * 打印 log
     */
    fun d(tag: String = TAG, msg: String) {
        if (DEBUG) {
            Log.d(tag, msg)
        }
    }


}