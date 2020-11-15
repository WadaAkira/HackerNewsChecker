package com.example.hackernewschecker.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewschecker.AppActivity
import com.example.hackernewschecker.AppApplication
import com.example.hackernewschecker.R
import com.example.hackernewschecker.common.view.CardListAdapter
import com.example.hackernewschecker.common.view.CardListDecoration
import com.example.hackernewschecker.databinding.MainFragmentBinding
import com.example.hackernewschecker.usecase.domain.News
import com.example.hackernewschecker.util.Log
import com.example.hackernewschecker.util.showToast
import javax.inject.Inject

/**
 * Hacker News API と通信し、結果を表示するフラグメント
 */
class MainFragment : Fragment(), MainContract.View {
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("MainFragmentBinding is null.")

    @Inject
    internal lateinit var presenter: MainContract.Presenter

    @Inject
    internal lateinit var adapter: CardListAdapter

    companion object {
        /**
         * フラグメントを生成する
         */
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as? AppApplication)
            ?.appComponent
            ?.inject(this)
            ?: throw IllegalStateException("Application or Activity is null.")

        presenter.setup(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SwipeRefreshLayout の処理
        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = true
            presenter.loadPage()
            binding.swipeLayout.isRefreshing = false
        }

        // RecyclerView とイベントハンドリングの実装
        binding.recyclerView.also {
            it.adapter = adapter
            it.addItemDecoration(CardListDecoration())
            it.addOnScrollListener(ScrollListener())
        }

        binding.errorMsg.setOnClickListener {
            adapter.clearNewsList()
            presenter.loadPage()
        }
        adapter.newsCallback = { news ->
            presenter.openNewsSite(news)
        }

        presenter.loadPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.stop()
        _binding = null
    }

    // 以下、View 実装
    override fun showLoading() {
        binding.errorMsg.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progress.visibility = View.GONE
    }

    override fun clearNews() {
        adapter.clearNewsList()
    }

    override fun showNewsList(newsList: List<News>) {
        adapter.setNewsList(newsList)
    }

    override fun transitNewsSite(url: Uri) {
        (activity as? AppActivity)?.startWebBrowser(url)
    }

    override fun showError(throwable: Throwable) {
        binding.errorMsg.visibility = View.VISIBLE
        Log.e("Failed to get hacker news. ${throwable.message}")
    }

    override fun showErrorToast(throwable: Throwable) {
        val context = context ?: return
        context.showToast(R.string.error_invalid_url)
        Log.e("Failed to open web site. ${throwable.message}")
    }
    // View 実装ここまで

    // RecyclerView のスクロール検出用のリスナー実装
    inner class ScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (!recyclerView.canScrollVertically(1)) {
                presenter.loadNext()
            }
        }
    }
    // RecyclerView のスクロール検出用のリスナー実装ここまで
}