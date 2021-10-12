package com.chendroid.learn.bean

/**
 * @intro 文章列表基础数据
 * @author zhaochen @ Zhihu Inc.
 * @since  2019/5/16
 */
data class ArticleListData(
    var offset: Int,
    var size: Int,
    var total: Int,
    var pageCount: Int,
    var curPage: Int,
    var over: Boolean,
    var datas: List<ArticleInfoData>?
)