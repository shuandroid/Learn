package com.chendroid.learn.bean


/**
 * @intro 文章的基本数据
 * @author zhaochen @ Zhihu Inc.
 * @since  2019/5/16
 */
data class ArticleInfoData(
    var id: Int,
    /**
     * -1 代表不需要该值
     */
    var originId: Int = -1,
    var title: String,
    var chapterId: Int,
    var chapterName: String?,
    var envelopePic: Any,
    var link: String,
    var author: String,
    var origin: Any,
    var publishTime: Long,
    var zan: Any,
    var desc: Any,
    var visible: Int,
    var niceDate: String,
    var courseId: Int,
    var collect: Boolean,
    var shareUser: String = ""
)