package com.chendroid.learn.core

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description : 父类 fragment
 */
open class BaseFragment : Fragment() {

    private lateinit var hostActivity: AppCompatActivity

    /** fragment 范围的 ViewModelProvider */
    private val fragmentViewModelProvider: ViewModelProvider by lazy {
        ViewModelProvider(this)
    }

    private val activityViewModelProvider: ViewModelProvider by lazy {
        ViewModelProvider(hostActivity)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        // todo 这里要不要做判断
        hostActivity = context as AppCompatActivity
    }

    protected fun <T : ViewModel> getFragmentScopeViewModel(modelClass: Class<T>): T {
        return fragmentViewModelProvider.get(modelClass)
    }

    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        return activityViewModelProvider.get(modelClass)
    }

}