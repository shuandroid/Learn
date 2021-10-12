package com.chendroid.learn.ui.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chendroid.learn.R
import com.chendroid.learn.bean.ArticleInfoData
import com.chendroid.learn.databinding.LayoutArticleListItemBinding
import com.chendroid.learn.ui.activity.ArticleDetailActivity
import com.drakeet.multitype.ItemViewDelegate

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description : 文章 item 的数据基本类
 */
class ArticleItemViewDelegate :
    ItemViewDelegate<ArticleInfoData, CommonViewHolder<LayoutArticleListItemBinding>>() {

    override fun onBindViewHolder(
        holder: CommonViewHolder<LayoutArticleListItemBinding>,
        data: ArticleInfoData
    ) {
        holder.binding.homeItemAuthor.text = data.author.ifEmpty { data.shareUser }
        holder.binding.articleTitle.text = data.title
        holder.binding.homeItemDate.text = data.niceDate
        holder.binding.homeItemType.tagText = data.chapterName
        holder.binding.homeItemHead.setActualImageResource(R.drawable.account_avatar)
        if (data.collect) {
            //收藏了该文章 todo
            holder.binding.homeItemLike.setImageResource(R.drawable.ic_action_like)
        } else {
            holder.binding.homeItemLike.setImageResource(R.drawable.ic_action_no_like)
        }
        holder.binding.root.setOnClickListener {
            holder.binding.root.context.startActivity(
                ArticleDetailActivity.args(
                    holder.binding.root.context,
                    data.id,
                    data.title,
                    data.link
                )
            )
        }

    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup
    ): CommonViewHolder<LayoutArticleListItemBinding> {
        return CommonViewHolder(
            LayoutArticleListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

}