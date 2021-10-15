package com.chendroid.learn.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chendroid.learn.api.ApiServiceHelper
import com.chendroid.learn.core.ResponseResult
import com.chendroid.learn.ui.data.source.CollectArticleDataSource
import com.chendroid.learn.ui.event.ArticleCollectInfoEvent
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author      : zhaochen
 * @date        : 2021/10/13
 * @description : 收藏文章的 ViewModel; 作为全局 application 内生效的 VM;
 * 因为想要在 ArticleListFragment 和 ArticleDetailActivity 中同时使用
 *
 */
class CollectArticleViewModel : ViewModel() {

    /**
     * 文章收藏状态改变的 id
     */
    private val _articleCollectChanged: MutableLiveData<ArticleCollectInfoEvent> =
        MutableLiveData()

    /**
     * 外部监听该 LiveData, 当发生变化时，根据 id 来更新对应的 UI 状态
     */
    val articleCollectChanged: LiveData<ArticleCollectInfoEvent> get() = _articleCollectChanged

    private val collectArticleDataSource: CollectArticleDataSource by lazy {
        CollectArticleDataSource(ApiServiceHelper.newWanService)
    }

    private fun updateCollectChangedId(articleCollectInfoEvent: ArticleCollectInfoEvent) {
        _articleCollectChanged.value = articleCollectInfoEvent
    }

    /**
     * 收藏文章
     */
    fun collectArticle(articleId: Int, handleResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val responseResult = collectArticleDataSource.collectArticle(articleId)
            if (responseResult is ResponseResult.Success) {
                withContext(Main) {
                    handleResult(true)
                    updateCollectChangedId(ArticleCollectInfoEvent(articleId, true))
                }
            } else {
                withContext(Main) {
                    handleResult(false)
                }
            }
        }
    }

    fun unCollectArticleInList(articleId: Int, handleResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val responseResult = collectArticleDataSource.unCollectArticleInList(articleId)
            if (responseResult is ResponseResult.Success) {
                withContext(Main) {
                    handleResult(true)
                    updateCollectChangedId(ArticleCollectInfoEvent(articleId, false))
                }
            } else {
                withContext(Main) {
                    handleResult(false)
                }
            }
        }
    }

    /**
     * 取消收藏文章, 在收藏界面调用
     */
    fun unCollectArticle(articleId: Int, originId: Int, handleResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val responseResult = collectArticleDataSource.unCollectArticle(articleId, originId)
            if (responseResult is ResponseResult.Success) {
                withContext(Main) {
                    handleResult(true)
                    updateCollectChangedId(ArticleCollectInfoEvent(articleId, false))
                }
            } else {
                withContext(Main) {
                    handleResult(false)
                }
            }
        }
    }


}