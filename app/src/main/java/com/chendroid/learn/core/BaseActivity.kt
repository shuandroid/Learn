package com.chendroid.learn.core

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chendroid.learn.LearnApp

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description :
 */
open class BaseActivity: AppCompatActivity() {

    /** activity 范围的 ViewModelProvider */
    private val activityViewModelProvider : ViewModelProvider by lazy {
        ViewModelProvider(this)
    }

    /** application 范围的 ViewModelProvider */
    private val appViewModelProvider : ViewModelProvider by lazy {
        ViewModelProvider(this.applicationContext as LearnApp)
    }

    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        return activityViewModelProvider.get(modelClass)
    }

    protected fun <T: ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        return appViewModelProvider.get(modelClass)
    }

}