package com.example.hackernewschecker.history

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hackernewschecker.HackerNewsCheckerActivity
import com.example.hackernewschecker.HackerNewsCheckerApplication
import com.example.hackernewschecker.R
import com.example.hackernewschecker.common.view.CardListAdapter
import com.example.hackernewschecker.common.view.CardListDecoration
import com.example.hackernewschecker.databinding.HistoryFragmentBinding
import com.example.hackernewschecker.usecase.domain.News
import com.example.hackernewschecker.util.Log
import com.example.hackernewschecker.util.showToast
import javax.inject.Inject

/**
 * 履歴画面を表示するフラグメント
 */
class HistoryFragment : Fragment(), HistoryContract.View {
    private var _binding: HistoryFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("HistoryFragmentBinding is null.")

    @Inject
    internal lateinit var presenter: HistoryContract.Presenter

    @Inject
    internal lateinit var adapter: CardListAdapter

    companion object {
        /**
         * フラグメントを生成する
         */
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as? HackerNewsCheckerApplication)
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
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView とイベントハンドリングの実装
        binding.recyclerView.also {
            it.adapter = adapter
            it.addItemDecoration(CardListDecoration())
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

    // 以下、HistoryContract.View 実装
    override fun showLoading() {
        binding.errorMsg.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progress.visibility = View.GONE
    }

    override fun showHistoryList(historyList: List<News>) {
        adapter.setNewsList(historyList)
    }

    override fun transitNewsSite(url: Uri) {
        (activity as? HackerNewsCheckerActivity)?.startWebBrowser(url)
    }

    override fun showNoneHistory() {
        binding.noneMsg.visibility = View.VISIBLE
    }

    override fun showToDeleteHistory(newsId: Int, title: String) {
        val context = context ?: return
        adapter.removeNews(newsId)
        context.showToast(context.getString(R.string.deleted_history, title))
    }

    override fun showError(throwable: Throwable) {
        binding.errorMsg.visibility = View.VISIBLE
        Log.e("Failed to get history list. ${throwable.message}")
    }

    override fun showErrorToast(throwable: Throwable) {
        val context = context ?: return
        context.showToast(R.string.error_invalid_url)
        Log.e("Failed to open web site. ${throwable.message}")
    }
    // 以下、HistoryContract.View 実装ここまで
}