package com.chendroid.learn.ui.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chendroid.learn.R
import com.chendroid.learn.bean.CollectArticleData
import com.chendroid.learn.databinding.CollectItemLayoutBinding
import com.drakeet.multitype.ItemViewDelegate

/**
 * @author      : zhaochen
 * @date        : 2021/10/21
 * @description : 收藏的文章 item 布局渲染
 */
class CollectItemViewDelegate :
    ItemViewDelegate<CollectArticleData, CommonViewHolder<CollectItemLayoutBinding>>() {

    interface CollectItemClickListener {
        fun onRootClicked(data: CollectArticleData)
        fun onLikeArticleClicked(
            data: CollectArticleData,
            handleResult: (isNetSuccess: Boolean) -> Unit
        )
    }

    var clickListener: CollectItemClickListener? = null

    override fun onBindViewHolder(
        holder: CommonViewHolder<CollectItemLayoutBinding>,
        data: CollectArticleData
    ) {
        holder.binding.homeItemAuthor.text = data.author
        holder.binding.articleTitle.text = data.title
        holder.binding.homeItemDate.text = data.niceDate
        holder.binding.homeItemType.tagText = data.chapterName
        holder.binding.homeItemHead.setActualImageResource(R.drawable.account_avatar)
        //收藏了该文章 todo
        holder.binding.homeItemLike.setImageResource(R.drawable.ic_action_like)

        holder.binding.root.setOnClickListener {
            clickListener?.run {
                onRootClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup
    ): CommonViewHolder<CollectItemLayoutBinding> {
        return CommonViewHolder(
            CollectItemLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

}