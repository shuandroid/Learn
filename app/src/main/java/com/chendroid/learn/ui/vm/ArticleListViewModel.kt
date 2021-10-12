package com.chendroid.learn.ui.vm

import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chendroid.learn.api.ApiServiceHelper
import com.chendroid.learn.bean.ArticleInfoData
import com.chendroid.learn.core.ResponseResult
import com.chendroid.learn.ui.data.repo.ArticleListRepo
import com.chendroid.learn.ui.data.source.ArticleListDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description : 文章列表 viewModel; fragment 内生效
 */
class ArticleListViewModel : ViewModel() {


    private val articleListRepo by lazy {
        ArticleListRepo(ArticleListDataSource(ApiServiceHelper.newWanService))
    }

    // 是否正在拉取数据，防止重复调用刷新
    var isLoadingArticle = false

    // 目前检索的文章页数
    var curPage = 0

    private val _articleListLiveData = MutableLiveData<List<ArticleInfoData>>()

    val articleListLiveData: LiveData<List<ArticleInfoData>>
        get() = _articleListLiveData

    /**
     * 获取文章列表
     */
    fun getArticleList(isFirstPage: Boolean = true) {
        var tempPage = 0
        tempPage = if (isFirstPage) {
            0
        } else {
            curPage
        }
        isLoadingArticle = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = articleListRepo.getArticleList(tempPage)
            Log.i("zc_test", "11111 current thread is ${Thread.currentThread()}")
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
    private fun emitUIArticleList(articleList: List<ArticleInfoData>) {
        isLoadingArticle = false
//        val articleList = arrayListOf<ArticleInfoData>()
        _articleListLiveData.value = articleList
    }


}