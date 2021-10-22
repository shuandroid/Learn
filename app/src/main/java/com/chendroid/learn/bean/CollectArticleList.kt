package com.chendroid.learn.bean

/**
 * @author      : zhaochen
 * @date        : 2021/10/15
 * @description : 收藏的文章数据类型， 用于获取「我的收藏」文章时使用
 */
/**
 * 收藏文章列表的返回值
 */
data class CollectResponse(
    val errorCode: Int,
    val errorMsg: String,
    val data: CollectArticleList?
)

// 收藏文章列表
data class CollectArticleList(
    val curPage: Int,
    val datas: List<CollectArticleData>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

// 收藏文章的数据 info 类
data class CollectArticleData(
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val originId: Int,
    val publishTime: Long,
    val title: String,
    val userId: Int,
    val visible: Int,
    val zan: Int
)
