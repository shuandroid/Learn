package com.chendroid.learn.ui.data.source

import com.chendroid.learn.api.NewWanService
import com.chendroid.learn.bean.CollectArticleResponse
import com.chendroid.learn.core.ResponseResult

/**
 * @author      : zhaochen
 * @date        : 2021/10/13
 * @description : 收藏文章相关的数据来源
 */
class CollectArticleDataSource(private val newWanService: NewWanService) {

    /**
     * 收藏文章
     */
    suspend fun collectArticle(articleId: Int): ResponseResult<CollectArticleResponse> {

        val response = newWanService.collectArticle(articleId.toString())

        if (response.isSuccessful) {
            val body = response.body()
            body?.run {
                return ResponseResult.Success(this)
            }
        }

        return ResponseResult.Error(Exception("收藏文章 失败 error code ${response.code()} error body is ${response.errorBody()} "))

    }

    /**
     * 取消收藏， 在文章列表中调用
     */
    suspend fun unCollectArticleInList(articleId: Int): ResponseResult<CollectArticleResponse> {
        val response = newWanService.unCollectArticleInArticleList(articleId.toString())
        if (response.isSuccessful) {
            val body = response.body()
            body?.run {
                return ResponseResult.Success(this)
            }
        }

        return ResponseResult.Error(Exception("收藏文章 失败 error code ${response.code()} error body is ${response.errorBody()} "))
    }

    /**
     * 取消收藏， 在收藏界面调用
     */
    suspend fun unCollectArticle(
        articleId: Int,
        originId: Int
    ): ResponseResult<CollectArticleResponse> {

        var originIdReal = -1
        if (originId > 0) {
            originIdReal = originId
        }
        val response = newWanService.unCollectArticle(articleId.toString(), originIdReal)
        if (response.isSuccessful) {
            val body = response.body()
            body?.run {
                return ResponseResult.Success(this)
            }
        }

        return ResponseResult.Error(Exception("收藏文章 失败 error code ${response.code()} error body is ${response.errorBody()} "))
    }
}