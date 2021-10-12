package com.chendroid.learn.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.LinearLayout
import com.chendroid.learn.core.BaseActivity
import com.chendroid.learn.databinding.ArticleDetailLayoutBinding
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

        /**
         * 构建打开 ArticleDetailActivity 的 intent
         */
        fun args(
            context: Context,
            articleId: Int,
            articleTitle: String,
            articleUrl: String
        ): Intent {
            return Intent(context, ArticleDetailActivity::class.java).apply {
                putExtra(ARTICLE_ID_KEY, articleId)
                putExtra(ARTICLE_TITLE_KEY, articleTitle)
                putExtra(ARTICLE_URL_KEY, articleUrl)
            }
        }

    }

    private lateinit var binding: ArticleDetailLayoutBinding

    private lateinit var agentWeb: AgentWeb

    private var articleId = 0
    private var articleTitle = ""
    private var articleUrl = ""


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
    }

    private fun parseIntent() {
        intent.extras?.run {
            articleId = getInt(ARTICLE_ID_KEY, 0)
            articleTitle = getString(ARTICLE_TITLE_KEY, "")
            articleUrl = getString(ARTICLE_URL_KEY, "")
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