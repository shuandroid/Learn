package com.chendroid.learn.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.widget.LinearLayout
import com.chendroid.learn.R
import com.chendroid.learn.core.BaseActivity
import com.chendroid.learn.databinding.ArticleDetailLayoutBinding
import com.chendroid.learn.ui.vm.CollectArticleViewModel
import com.chendroid.learn.util.DebugLog
import com.chendroid.learn.util.toast
import com.just.agentweb.AgentWeb

/**
 * @author      : zhaochen
 * @date        : 2021/9/3
 * @description : 文章详情页
 */
class ArticleDetailActivity : BaseActivity() {

    companion object {
        /**
         * url key
         */
        const val ARTICLE_URL_KEY = "url"

        /**
         * title key
         */
        const val ARTICLE_TITLE_KEY = "title"

        /**
         * id key
         */
        const val ARTICLE_ID_KEY = "id"

        const val ARTICLE_COLLECT_KEY = "collect"

        /**
         * 构建打开 ArticleDetailActivity 的 intent
         */
        fun args(
            context: Context,
            articleId: Int,
            articleTitle: String,
            articleUrl: String,
            collect: Boolean = false
        ): Intent {
            return Intent(context, ArticleDetailActivity::class.java).apply {
                putExtra(ARTICLE_ID_KEY, articleId)
                putExtra(ARTICLE_TITLE_KEY, articleTitle)
                putExtra(ARTICLE_URL_KEY, articleUrl)
                putExtra(ARTICLE_COLLECT_KEY, collect)
                putExtra("", Bundle())
            }
        }

    }

    private lateinit var binding: ArticleDetailLayoutBinding

    private lateinit var agentWeb: AgentWeb

    private var articleId = 0
    private var articleTitle = ""
    private var articleUrl = ""
    private var collect = false

    // 收藏文章的 viewModel
    private val collectArticleViewModel: CollectArticleViewModel by lazy {
        getApplicationScopeViewModel(CollectArticleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseIntent()
        binding = ArticleDetailLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initView()
        initAgentWeb()
    }

    private fun initView() {

        binding.toolbar.apply {
            title = articleTitle.ifEmpty { "加载中" }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { finish() }
        }

        Handler().post {  }
        binding.collectButton.apply {
            setImageResource(if (collect) R.drawable.ic_action_like else R.drawable.ic_action_no_like)
            setOnClickListener {
                DebugLog.d("zc_test", "文章详情页，收藏按钮点击")
                if (collect) {
                    toast("已经收藏该文章，长按取消收藏")
                } else {
                    // 收藏文章
                    collectArticle()
                    setImageResource(R.drawable.ic_action_like)
                }
            }
            setOnLongClickListener {
                DebugLog.d("zc_test", "文章详情页，收藏按钮长按～～～～～点击")
                if (collect) {
                    unCollectArticle()
                    setImageResource(R.drawable.ic_action_no_like)
                }
                true
            }
        }
    }

    private fun collectArticle() {
        collectArticleViewModel.collectArticle(articleId) { isNetSuccess ->
            if (isNetSuccess) {
                DebugLog.d("zc_test", "文章详情页，收藏按钮,收藏成功")
                toast("收藏成功了")
            } else {
                DebugLog.d("zc_test", "文章详情页，收藏按钮,收藏失败了～")
                toast("收藏失败了， 重置 view 状态")
                binding.collectButton.setImageResource(R.drawable.ic_action_no_like)
            }
        }
    }

    private fun unCollectArticle() {
        collectArticleViewModel.unCollectArticleInList(articleId) { isNetSuccess ->
            if (isNetSuccess) {
                DebugLog.d("zc_test", "文章详情页，收藏按钮,取消收藏成功")
                toast("已取消收藏")
            } else {
                DebugLog.d("zc_test", "文章详情页，收藏按钮,收藏失败了～")
                toast("收藏失败了， 重置 view 状态")
                binding.collectButton.setImageResource(R.drawable.ic_action_like)
            }
        }
    }

    private fun parseIntent() {
        intent.extras?.run {
            articleId = getInt(ARTICLE_ID_KEY, 0)
            articleTitle = getString(ARTICLE_TITLE_KEY, "")
            articleUrl = getString(ARTICLE_URL_KEY, "")
            collect = getBoolean(ARTICLE_COLLECT_KEY, false)
        }
    }

    /**
     * 初始化 webView 数据
     */
    private fun initAgentWeb() {
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.webContent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb().go(articleUrl)
    }

    // region 实现 webView 跟随 Activity 生命周期
    override fun onRestart() {
        agentWeb.webLifeCycle.onResume()
        super.onRestart()
    }

    override fun onPause() {
        agentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (agentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }
    // endregion


}