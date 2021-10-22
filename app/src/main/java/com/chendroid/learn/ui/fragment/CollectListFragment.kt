package com.chendroid.learn.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chendroid.learn.bean.CollectArticleData
import com.chendroid.learn.core.BaseFragment
import com.chendroid.learn.databinding.FragmentArticleListLayoutBinding
import com.chendroid.learn.ui.activity.ArticleDetailActivity
import com.chendroid.learn.ui.event.ArticleCollectInfoEvent
import com.chendroid.learn.ui.holder.CollectItemViewDelegate
import com.chendroid.learn.ui.vm.CollectArticleViewModel
import com.chendroid.learn.ui.vm.CollectListViewModel
import com.chendroid.learn.util.DebugLog
import com.chendroid.learn.widget.CustomItemDecoration
import com.drakeet.multitype.MultiTypeAdapter

/**
 * @author      : zhaochen
 * @date        : 2021/10/15
 * @description : 「我的收藏」文章列表界面
 */
class CollectListFragment : BaseFragment(), CollectItemViewDelegate.CollectItemClickListener {

    companion object {
        /**
         * 剩余文章触发 load more 的个数
         */
        const val ARTICLE_LAST_TRIGGER_NUMBER = 5

        /**
         * 在这里传递一些数据，通过 bundle 设置给 fragment
         */
        fun newInstance(): CollectListFragment {
            val bundle = Bundle()
            bundle.putString("targetName", "ArticleListFragment")
            val fragment = CollectListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    // 文章列表 recyclerView 对应的 adapter
    private val multiTypeAdapter = MultiTypeAdapter()

    private var _binding: FragmentArticleListLayoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var articleListRecyclerView: RecyclerView

    private val collectListViewModel: CollectListViewModel by lazy {
        getFragmentScopeViewModel(CollectListViewModel::class.java)
    }

    // 收藏文章的 viewModel， 在 application 范围内生效
    private val collectArticleViewModel: CollectArticleViewModel by lazy {
        getApplicationScopeViewModel(CollectArticleViewModel::class.java)
    }

    // 真正用来存在文章列表需要的数据
    private val collectList: MutableList<CollectArticleData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        multiTypeAdapter.register(CollectItemViewDelegate().apply {
            // 设置监听回调
            clickListener = this@CollectListFragment
        })
        multiTypeAdapter.items = collectList
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

        initHomeSwipeRefresh()
        initRecyclerView()

        bindViewModel()
        refreshData()
    }

    private fun initHomeSwipeRefresh() {
        binding.homeSwipeRefresh.apply {
            isRefreshing = true
        }.setOnRefreshListener {
            DebugLog.d(msg = " Collect list homeSwipeRefresh refresh ")
            refreshData()
        }
    }

    private fun initRecyclerView() {
        binding.homeRecyclerView.apply {
            DebugLog.d(msg = "homeRecyclerView 的 overScrollMode  is $overScrollMode")
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
                this@CollectListFragment.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun bindViewModel() {

        collectListViewModel.collectListLiveData.observe(viewLifecycleOwner, {
            DebugLog.d(msg = "监听得到 数据")
            collectList.clear()
            collectList.addAll(it!!)
            multiTypeAdapter.notifyDataSetChanged()
            binding.homeSwipeRefresh.isRefreshing = false
        })

        collectArticleViewModel.articleCollectChanged.observe(viewLifecycleOwner, {
            DebugLog.d("zc_test", "收到文章状态发生变化， articleId is ${it.articleId}")
            updateArticleStatusByLV(it)
        })
    }

    private fun refreshData() {
        binding.homeSwipeRefresh.isRefreshing = true
        collectListViewModel.getCollectList(true)
    }

    private fun loadMoreData() {
        collectListViewModel.getCollectList(false)
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

            return totalItemCount > 0 && totalItemCount - lastVisibleItemPosition - 1 <= ArticleListFragment.ARTICLE_LAST_TRIGGER_NUMBER
        }

        return false
    }

    /**
     * 判断是否可以加载数据
     * 防止重复拉取数据
     */
    private fun canLoadMore(): Boolean {
        return !collectListViewModel.isLoading
    }

    /**
     * 根据 liveData 的监听，改变文章 item 的状态
     * todo 待补充此时逻辑
     */
    private fun updateArticleStatusByLV(articleCollectInfoEvent: ArticleCollectInfoEvent) {
        var position = 0
        var collectArticleData: CollectArticleData? = null
        collectList.forEach {
            if (it.id == articleCollectInfoEvent.articleId) {
                collectArticleData = it
                return@forEach
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 要不要销毁
        _binding = null
    }

    //region 实现的接口的重写方法
    override fun onRootClicked(data: CollectArticleData) {
        DebugLog.d("zc_test", "onRootClicked() data is $data")
        if (context == null) {
            return
        }
        startActivity(
            ArticleDetailActivity.args(
                requireContext(),
                data.id,
                data.title,
                data.link,
                true
            )
        )
    }

    override fun onLikeArticleClicked(
        data: CollectArticleData,
        handleResult: (isNetSuccess: Boolean) -> Unit
    ) {
        DebugLog.d("zc_test", "onLikeArticleClicked() data is $data")
//        if (data.collect) {
//            collectArticleViewModel.unCollectArticleInList(data.id, handleResult)
//        } else {
//            collectArticleViewModel.collectArticle(data.id, handleResult)
//        }
    }
    //endregion


}