package com.chendroid.learn.bean

/**
 * @intro: 文章列表返回数据类
 * @author zhaochen @ Zhihu Inc.
 * @since  2019/5/16
 */
data class ArticleListResponse(
    var errorCode: Int,
    var errorMsg: String?,
    val data: ArticleListData
)