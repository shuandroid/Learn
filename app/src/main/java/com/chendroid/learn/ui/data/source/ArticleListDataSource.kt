package com.chendroid.learn.ui.data.source

import com.chendroid.learn.api.NewWanService
import com.chendroid.learn.bean.ArticleListResponse
import com.chendroid.learn.core.ResponseResult

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description : 获取文章的数据来源类
 */
class ArticleListDataSource(private val newWanService: NewWanService) {

    suspend fun getArticleList(page: Int): ResponseResult<ArticleListResponse> {

        val articleResult = newWanService.getArticleList(page)

        if (articleResult.isSuccessful) {
            val body = articleResult.body()
            body?.run {
                return ResponseResult.Success(this)
            }
        }

        return ResponseResult.Error(Exception("获取 banner 失败 error code ${articleResult.code()} error body is ${articleResult.errorBody()} "))
    }

}