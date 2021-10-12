package com.chendroid.learn.core

import android.app.Application
import android.content.Context

/**
 * @author      : zhaochen
 * @date        : 2021/8/24
 * @description : SP 数据的帮助类，后续简化操作
 */
object PreferenceHelper {

    private lateinit var application : Application

    fun initSp(app: Application) {
        this.application = app
    }


    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(fileName: String, keyName:String, default: T) : T{
        val sp = application.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val value = when (default) {
            is Long -> sp.getLong(keyName, default)
            is String -> sp.getString(keyName, default)
            is Int -> sp.getInt(keyName, default)
            is Boolean -> sp.getBoolean(keyName, default)
            is Float -> sp.getFloat(keyName, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
        return value as T
    }

    fun <T> setValue(fileName: String, keyName:String, value: T) {
        val sp = application.getSharedPreferences(fileName, Context.MODE_PRIVATE)

        when (value) {
            is Long -> sp.edit().putLong(keyName, value).apply()
            is String -> sp.edit().putString(keyName, value).apply()
            is Int -> sp.edit().putInt(keyName, value).apply()
            is Boolean -> sp.edit().putBoolean(keyName, value).apply()
            is Float -> sp.edit().putFloat(keyName, value).apply()
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }

    }



}