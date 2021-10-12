package com.chendroid.learn.ui.holder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description : 如果使用了 view binding ， 则 RecyclerView 的 viewHolder 可以做一个通用的，不需要每个 item 都创建一个 view holder
 */
open class CommonViewHolder<V : ViewBinding>(val binding: V) :
    RecyclerView.ViewHolder(binding.root) {
}