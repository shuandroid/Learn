package com.chendroid.learn.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * @author      : zhaochen
 * @date        : 2021/8/25
 * @description : view pager 的 adapter
 */
class MainFragmentViewPagerAdapter  (var titleList: List<String>, var fragmentList: List<Fragment>, fm: FragmentManager) : FragmentStatePagerAdapter(fm,
    BEHAVIOR_SET_USER_VISIBLE_HINT) {

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }


}