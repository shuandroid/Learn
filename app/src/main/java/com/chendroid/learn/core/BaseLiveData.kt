package com.chendroid.learn.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.ConcurrentHashMap

/**
 * @author : chen
 * @date : 2021/8/3
 * @description : 对 LiveData 做二次封装, 添加 state 状态，只有在 state =  true 时，才响应 liveData 的 onChanged() 变化
 */
open class BaseLiveData<T> : LiveData<T>() {

    protected var isAllowNullValue = false

    private val observerMap: ConcurrentHashMap<Observer<in T>, ObserverProxy> = ConcurrentHashMap()

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val observerProxy = getObserverProxy(observer)
        observerProxy?.run {
            //这里使用代码，代替原来的 observer； 即 A 观察了 LiveDataA, B 同样观察了 LiveDataA,
            super.observe(owner, this)
        }
    }

    override fun observeForever(observer: Observer<in T>) {
        getObserverProxy(observer)?.run {
            super.observeForever(this)
        }
    }


    override fun setValue(value: T) {
        if (value != null || isAllowNullValue) {
            for (entry in observerMap.entries) {
                // 每个 proxy 的 state 都设置为 true
                // 如果 state 为 false, 则外部的监听者接受不到本次 liveData 的数据
                entry.value.state = true
            }
        }
        super.setValue(value)
    }

    override fun removeObserver(observer: Observer<in T>) {

        var proxy: Observer<in T>?
        var target: Observer<in T>?

        if (observer is ObserverProxy) {
            // 如果设置了代理类
            proxy = observer
            target = observer.target
        } else {
            proxy = observerMap.get(observer)
            if (proxy != null) {
                target = observer
            } else {
                target = null
            }
        }

        if (proxy != null && target != null) {
            observerMap.remove(target)
            super.removeObserver(observer)
        }
    }

    fun clear() {
        super.setValue(null)
    }

    /**
     * 返回observer 代理的 proxy 类
     */
    private fun getObserverProxy(observer: Observer<in T>): Observer<in T>? {
        if (observerMap.contains(observer)) {
            // todo 这里可添加强制判断，为 null 时，不需要后续操作，直接返回
            return null
        } else {
            val observerProxy = ObserverProxy(observer)
            observerMap[observer] = observerProxy
            return observerProxy
        }
    }


    private inner class ObserverProxy(target: Observer<in T>) : Observer<T> {
        val target: Observer<in T> = target

        /**
         * state 很重要，只有在为 true 的情况下，liveData  onChanged() 回调时，真正的监听这的 target.onChanged(t) 才会被触发；
         * 即： 一个刚注册的 observer 收到 liveData 上一次数据时，但是 state =  false ,从而规避调这次数据的更新
         */
        var state = false

        override fun onChanged(t: T?) {
            val proxy: ObserverProxy? = observerMap[target]

            if (proxy != null && proxy.state) {
                proxy.state = false
                if (t != null || isAllowNullValue) {
                    target.onChanged(t)
                }
            }
        }

    }

}