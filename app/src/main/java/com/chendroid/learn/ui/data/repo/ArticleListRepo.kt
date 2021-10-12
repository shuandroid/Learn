package com.chendroid.learn.ui.data.repo

import com.chendroid.learn.bean.ArticleListData
import com.chendroid.learn.core.ResponseResult
import com.chendroid.learn.ui.data.source.ArticleListDataSource

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description :
 */
class ArticleListRepo(val articleListDataSource: ArticleListDataSource) {

    suspend fun getArticleList(page: Int = 0): ResponseResult<ArticleListData> {
        val result = articleListDataSource.getArticleList(page)
        if (result is ResponseResult.Success) {
            result.data.data.run {
                return ResponseResult.Success(this)
            }
        }

        return ResponseResult.Error(Exception(result.toString()))
    }

}