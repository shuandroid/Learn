package com.chendroid.learn.util

import android.content.Context
import android.content.res.Resources
import android.widget.Toast

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description : 存放扩展函数
 */


val Number.dp get() = (toInt() * Resources.getSystem().displayMetrics.density).toInt()

fun Context.toast(content: String) {
    Toast.makeText(this.applicationContext, content, Toast.LENGTH_SHORT).show()
}

