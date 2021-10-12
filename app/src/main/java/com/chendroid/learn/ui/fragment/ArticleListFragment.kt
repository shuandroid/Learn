package com.chendroid.learn.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chendroid.learn.core.BaseFragment
import com.chendroid.learn.databinding.FragmentArticleListLayoutBinding
import com.chendroid.learn.ui.holder.ArticleItemViewDelegate
import com.chendroid.learn.ui.vm.ArticleListViewModel
import com.chendroid.learn.util.DebugLog
import com.chendroid.learn.widget.CustomItemDecoration
import com.drakeet.multitype.MultiTypeAdapter

/**
 * @author      : zhaochen
 * @date        : 2021/8/26
 * @description : 文章列表界面 fragment
 */
class ArticleListFragment : BaseFragment() {

    companion object {
        /**
         * 剩余文章触发 load more 的个数
         */
        const val ARTICLE_LAST_TRIGGER_NUMBER = 5

        /**
         * 在这里传递一些数据，通过 bundle 设置给 fragment
         */
        fun newInstance(): BaseFragment {
            val bundle = Bundle()
            bundle.putString("targetName", "ArticleListFragment")
            val fragment = ArticleListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentArticleListLayoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var articleListRecyclerView: RecyclerView

    // 文章列表 recyclerView 对应的 adapter
    private val multiTypeAdapter = MultiTypeAdapter()

    // 真正用来存在文章列表需要的数据
    private val articleList: MutableList<Any> = mutableListOf()

    private val articleListViewModel: ArticleListViewModel by lazy {
        getActivityScopeViewModel(ArticleListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        multiTypeAdapter.register(ArticleItemViewDelegate())
        multiTypeAdapter.items = articleList
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleListLayoutBinding.inflate(inflater, container, false)
        articleListRecyclerView = binding.homeRecyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 处理 view 内容

        initHomeSwipeRefresh()
        initRecyclerView()

        bindViewModel()
        refreshData()
    }

    private fun initHomeSwipeRefresh() {
        binding.homeSwipeRefresh.apply {
            isRefreshing = true
        }.setOnRefreshListener {
            DebugLog.d(msg = "homeSwipeRefresh refresh ")
            refreshData()
        }
    }

    private fun initRecyclerView() {
        binding.homeRecyclerView.apply {
            DebugLog.d(msg = "homeRecyclerViewv 的 overScrollMode  is $overScrollMode")
            overScrollMode
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = multiTypeAdapter
            // 设置 recyclerView item 分割条
            addItemDecoration(CustomItemDecoration.with(context))
        }

        // 文章列表的滚动监听
        binding.homeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                DebugLog.d("zc_test", "文章列表滚动了")
                this@ArticleListFragment.onScrolled(recyclerView, dx, dy)
            }
        })

    }

    private fun bindViewModel() {

        articleListViewModel.articleListLiveData.observe(viewLifecycleOwner, {
            DebugLog.d(msg = "监听得到 数据")
            articleList.addAll(it!!)
            multiTypeAdapter.notifyDataSetChanged()
            binding.homeSwipeRefresh.isRefreshing = false

        })
    }

    private fun refreshData() {
        binding.homeSwipeRefresh.isRefreshing = true
        articleList.clear()
        articleListViewModel.getArticleList(true)
    }

    private fun loadMoreData() {
        articleListViewModel.getArticleList(false)
    }


    // 滚动时会调用该方法，去判断是否需要加载新的数据 loadMore
    private fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy == 0) {
            return
        }

        if (isScrollingTriggerLoadingMore(recyclerView) && canLoadMore()) {
            recyclerView.post { loadMoreData() }
        }
    }

    /**
     * 只对 LinearLayoutManager 生效，其他 LayoutManager 请重写此方法
     */
    private fun isScrollingTriggerLoadingMore(recyclerView: RecyclerView): Boolean {
        val layoutManager = recyclerView.layoutManager

        layoutManager?.run {
            val totalItemCount = itemCount
            val lastVisibleItemPosition = if (this is LinearLayoutManager) {
                findLastVisibleItemPosition()
            } else {
                return false
            }

            return totalItemCount > 0 && totalItemCount - lastVisibleItemPosition - 1 <= ARTICLE_LAST_TRIGGER_NUMBER
        }

        return false
    }

    /**
     * 判断是否可以加载数据
     * 防止重复拉取数据
     */
    private fun canLoadMore(): Boolean {
        return !articleListViewModel.isLoadingArticle
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // 要不要销毁
        _binding = null
    }


}