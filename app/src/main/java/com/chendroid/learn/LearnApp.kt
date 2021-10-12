package com.chendroid.learn

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.chendroid.learn.core.PreferenceHelper
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.stetho.Stetho

/**
 * @author      : zhaochen
 * @date        : 2021/8/24
 * @description : app 的 Application
 */
class LearnApp: Application(), ViewModelStoreOwner {

    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        PreferenceHelper.initSp(this)
        Fresco.initialize(this)
        Stetho.initializeWithDefaults(this)
    }

    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }

}