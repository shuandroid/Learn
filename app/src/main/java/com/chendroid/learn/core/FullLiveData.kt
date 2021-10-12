package com.chendroid.learn.core

/**
 * @author      : zhaochen
 * @date        : 2021/8/4
 * @description : 不对外暴漏的 liveData 封装
 */
class FullLiveData<T> : BaseLiveData<T>() {

    override fun setValue(value: T) {
        super.setValue(value)
    }

    override fun postValue(value: T) {
        super.postValue(value)
    }

    class Builder<T> {
        /**
         * 是否允许传入 null value
         */
        private var isAllowNullValue = false
        fun setAllowNullValue(allowNullValue: Boolean): Builder<T> {
            isAllowNullValue = allowNullValue
            return this
        }

        fun create(): FullLiveData<T> {
            val liveData: FullLiveData<T> = FullLiveData()
            liveData.isAllowNullValue = isAllowNullValue
            return liveData
        }
    }

}