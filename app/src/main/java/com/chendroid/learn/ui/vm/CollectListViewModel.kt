package com.chendroid.learn.ui.vm

import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chendroid.learn.api.ApiServiceHelper
import com.chendroid.learn.bean.CollectArticleData
import com.chendroid.learn.core.ResponseResult
import com.chendroid.learn.ui.data.source.CollectListDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author      : zhaochen
 * @date        : 2021/10/15
 * @description : 我的收藏列表的 ViewModel
 */
class CollectListViewModel : ViewModel() {

    private val collectListDataSource: CollectListDataSource by lazy {
        CollectListDataSource(ApiServiceHelper.newWanService)
    }

    private val _collectListLiveData = MutableLiveData<List<CollectArticleData>>()

    val collectListLiveData: LiveData<List<CollectArticleData>>
        get() = _collectListLiveData

    // 是否正在拉取数据，防止重复调用刷新
    var isLoading = false

    // 目前检索的文章页数
    var curPage = 0

    /**
     * 获取收藏的文章列表
     */
    fun getCollectList(isFirstPage: Boolean) {
        var tempPage = 0
        tempPage = if (isFirstPage) {
            0
        } else {
            curPage
        }
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = collectListDataSource.getCollectList(tempPage)
            if (result is ResponseResult.Success) {
                curPage = result.data.curPage
                result.data.datas?.run {
                    withContext(Dispatchers.Main) {
                        Log.i("zc_test", "22222 current thread is ${Thread.currentThread()}")
                        emitUIArticleList(this@run)
                    }
                }
            } else if (result is ResponseResult.Error) {
                withContext(Dispatchers.Main) {
                    Log.i(
                        "zc_test",
                        "getArticleList get artile error , mes is ${result.toString()}"
                    )
                }
            }
        }
    }

    /**
     * 刷新文章列表
     */
    @UiThread
    private fun emitUIArticleList(collectList: List<CollectArticleData>) {
        isLoading = false
        _collectListLiveData.value = collectList
    }

}