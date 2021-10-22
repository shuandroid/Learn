package com.chendroid.learn.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author      : zhaochen
 * @date        : 2021/10/22
 * @description : ViewPager2 对应的 Fragment adapter
 */
class FragmentViewAdapter2(
    var fragmentList: List<Fragment>,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]

}