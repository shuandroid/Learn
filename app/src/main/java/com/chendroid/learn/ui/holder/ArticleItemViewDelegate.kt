package com.chendroid.learn.ui.holder

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.chendroid.learn.R
import com.chendroid.learn.bean.ArticleInfoData
import com.chendroid.learn.databinding.LayoutArticleListItemBinding
import com.chendroid.learn.util.DebugLog
import com.chendroid.learn.util.toast
import com.drakeet.multitype.ItemViewDelegate

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description : 文章 item 的数据基本类
 */
class ArticleItemViewDelegate :
    ItemViewDelegate<ArticleInfoData, CommonViewHolder<LayoutArticleListItemBinding>>() {

    interface ArticleItemClickListener {
        fun onRootClicked(data: ArticleInfoData)
        fun onLikeArticleClicked(
            data: ArticleInfoData,
            handleResult: (isNetSuccess: Boolean) -> Unit
        )
    }

    var clickListener: ArticleItemClickListener? = null
    private var animatorAliveSet: AnimatorSet? = null

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
            clickListener?.run {
                onRootClicked(data)
            }
        }

        holder.binding.homeItemLike.setOnClickListener {
            DebugLog.d("zc_test", "收藏按钮被点击 ${holder.binding.homeItemLike}")
            startLikeAnimator(it as ImageView, !data.collect)
            clickListener?.run {
                onLikeArticleClicked(data) { isNetSuccess ->
                    if (isNetSuccess) {
                        data.collect = !data.collect
                    } else {
                        DebugLog.d("zc_test", "收藏按钮被点击, 收藏网络失败")
                        holder.binding.homeItemLike.context.toast("收藏失败了")
                    }
                }
            }
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

    /**
     * 开启收藏喜欢动画， 难看的动画  「只有缩放」
     */
    private fun startLikeAnimator(articleLikeView: ImageView, isCollect: Boolean) {
        animatorAliveSet?.run {
            if (isRunning) {
                cancel()
            }
        }

        val scaleXAnim = ObjectAnimator.ofFloat(articleLikeView, "scaleX", 0.4f)

        val scaleYAnim = ObjectAnimator.ofFloat(articleLikeView, "scaleY", 0.4f)
        val animatorSet = AnimatorSet()
        val animatorNewSet = AnimatorSet()

        animatorSet.playTogether(scaleXAnim, scaleYAnim)
        animatorSet.duration = 300
        animatorSet.start()

        animatorAliveSet = animatorSet

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                Log.i("zc_test", "  111111 onAnimationEnd")
                if (isCollect) {
                    articleLikeView.setImageResource(R.drawable.ic_action_like)
                } else {
                    articleLikeView.setImageResource(R.drawable.ic_action_no_like)
                }
                animatorNewSet.start()
                animatorAliveSet = animatorNewSet
            }
        })

        val scaleXNewAnim = ObjectAnimator.ofFloat(articleLikeView, "scaleX", 0.4f, 1.2f, 1f)
        val scaleYNewAnim = ObjectAnimator.ofFloat(articleLikeView, "scaleY", 0.4f, 1.2f, 1f)

        animatorNewSet.playTogether(scaleXNewAnim, scaleYNewAnim)
        animatorNewSet.duration = 300
    }


}