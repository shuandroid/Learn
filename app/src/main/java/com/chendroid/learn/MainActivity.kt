package com.chendroid.learn

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.chendroid.learn.core.BaseActivity
import com.chendroid.learn.databinding.MainActivityBinding
import com.chendroid.learn.ui.adapter.MainFragmentViewPagerAdapter
import com.chendroid.learn.ui.fragment.AccountFragment
import com.chendroid.learn.ui.fragment.ArticleListFragment
import com.chendroid.learn.ui.main.MainFragment
import com.chendroid.learn.util.DebugLog
import com.chendroid.learn.widget.HomeToolbar
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
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }


        initView()
    }

    private fun initView() {
        var tabTitleList = listOf<String>("首页", "更多")
        val firstHomeFragment = ArticleListFragment.newInstance()
        val moreTypeFragment = MainFragment()
        var fragmentList = listOf<Fragment>(firstHomeFragment, moreTypeFragment)
        binding.mainViewPager.adapter =
            MainFragmentViewPagerAdapter(tabTitleList, fragmentList, supportFragmentManager)
        binding.mainTabLayout.apply {
            setupWithViewPager(binding.mainViewPager)
            isTabIndicatorFullWidth = false
        }

        binding.mainViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                DebugLog.d(msg = "mainViewPager onPageScrolled()")
            }

            override fun onPageSelected(position: Int) {
                DebugLog.d(msg = "mainViewPager onPageSelected() position is $position")
            }

            override fun onPageScrollStateChanged(state: Int) {
                DebugLog.d(msg = "mainViewPager onPageScrollStateChanged() state is $state")

            }
        })

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