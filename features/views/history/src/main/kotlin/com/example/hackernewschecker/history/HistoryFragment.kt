package com.example.hackernewschecker.history

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewschecker.common.interfaces.ActivityDependencyControl
import com.example.hackernewschecker.common.view.CardListAdapter
import com.example.hackernewschecker.common.view.CardListDecoration
import com.example.hackernewschecker.common.util.Log
import com.example.hackernewschecker.common.util.showToast
import com.example.hackernewschecker.dto.News
import com.example.hackernewschecker.history.databinding.HistoryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 履歴画面を表示するフラグメント
 */
@AndroidEntryPoint
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

        presenter.setup(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView の設定
        binding.recyclerView.also {
            it.adapter = adapter
            it.addItemDecoration(CardListDecoration())
        }

        // スワイプによる履歴の削除をイベントハンドリング
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val news = adapter.getNews(viewHolder.adapterPosition) ?: return
                presenter.deleteHistory(news)
            }
        }).attachToRecyclerView(binding.recyclerView)

        // エラーハンドリング
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
        (activity as? ActivityDependencyControl)?.startWebBrowser(url)
    }

    override fun showNoneHistory() {
        binding.noneMsg.visibility = View.VISIBLE
    }

    override fun showToDeleteHistory(newsId: Int, title: String) {
        val context = context ?: return
        adapter.removeNews(newsId)
        context.showToast(context.getString(R.string.deleted_history, title))

        // すべての要素を削除した場合、履歴がないことを表示する
        if (adapter.itemCount <= 0) {
            showNoneHistory()
        }
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