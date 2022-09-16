package com.chendroid.learn

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.chendroid.learn.core.BaseActivity
import com.chendroid.learn.databinding.MainActivityBinding
import com.chendroid.learn.ui.adapter.FragmentViewAdapter2
import com.chendroid.learn.ui.fragment.AccountFragment
import com.chendroid.learn.ui.fragment.ArticleListFragment
import com.chendroid.learn.ui.fragment.CollectListFragment
import com.chendroid.learn.ui.main.MainFragment
import com.chendroid.learn.util.DebugLog
import com.chendroid.learn.widget.HomeToolbar
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar

class MainActivity : BaseActivity() {

    lateinit var binding: MainActivityBinding

    val accountFragment: AccountFragment by lazy {
        AccountFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .statusBarAlpha(0.2f)
            .init()
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        var tabTitleList = listOf<String>("首页", "更多", "我的收藏")
        val firstHomeFragment = ArticleListFragment.newInstance()
        val moreTypeFragment = MainFragment()
        val collectListFragment = CollectListFragment.newInstance()
        var fragmentList =
            listOf<Fragment>(firstHomeFragment, moreTypeFragment, collectListFragment)
        binding.mainViewPager.apply {
            adapter = FragmentViewAdapter2(fragmentList, this@MainActivity)
        }

        TabLayoutMediator(binding.mainTabLayout, binding.mainViewPager) { tab, position ->
            tab.text = tabTitleList[position]
        }.attach()

        //ViewPager 的实现
//        binding.mainViewPager.adapter =
//            MainFragmentViewPagerAdapter(tabTitleList, fragmentList, supportFragmentManager)
//        binding.mainTabLayout.apply {
//            setupWithViewPager(binding.mainViewPager)
//            isTabIndicatorFullWidth = false
//        }

//        binding.mainViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                DebugLog.d(msg = "mainViewPager onPageScrolled()")
//            }
//
//            override fun onPageSelected(position: Int) {
//                DebugLog.d(msg = "mainViewPager onPageSelected() position is $position")
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//                DebugLog.d(msg = "mainViewPager onPageScrollStateChanged() state is $state")
//
//            }
//        })

//        binding.mainViewPager.scroll


        binding.homeToolbar.homeToolbarListener = object : HomeToolbar.HomeToolbarListener {
            override fun onAvatarViewClicked() {
                DebugLog.d(msg = "头像点击了")
                jumpToAccountPage()
            }
        }
    }

    private fun jumpToAccountPage() {
        binding.accountContentView.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .add(R.id.account_content_view, accountFragment).commitNow()
    }

    override fun onBackPressed() {
        if (binding.accountContentView.visibility == View.VISIBLE) {
            binding.accountContentView.visibility = View.INVISIBLE
            supportFragmentManager.beginTransaction().remove(accountFragment).commitNow()
        } else {
            super.onBackPressed()
        }
    }


}