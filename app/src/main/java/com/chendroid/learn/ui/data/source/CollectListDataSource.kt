package com.chendroid.learn.ui.data.source

import com.chendroid.learn.api.NewWanService
import com.chendroid.learn.bean.CollectArticleList
import com.chendroid.learn.core.ResponseResult

/**
 * @author      : zhaochen
 * @date        : 2021/10/15
 * @description :
 */
class CollectListDataSource(private val newWanService: NewWanService) {

    /**
     * 获取收藏文章列表
     */
    suspend fun getCollectList(page: Int = 0): ResponseResult<CollectArticleList> {
        val response = newWanService.getCollectArticleList(page)
        if (response.isSuccessful) {
            val body = response.body()
            body?.data?.run {
                return ResponseResult.Success(this)
            }
        }

        return ResponseResult.Error(Exception("获取收藏文章 失败 getCollectList error code ${response.code()} error body is ${response.errorBody()} "))

    }

}