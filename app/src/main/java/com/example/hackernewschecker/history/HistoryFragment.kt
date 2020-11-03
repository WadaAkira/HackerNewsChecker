package com.example.hackernewschecker.history

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hackernewschecker.HackerNewsCheckerApplication
import com.example.hackernewschecker.databinding.HistoryFragmentBinding
import com.example.hackernewschecker.usecase.domain.News
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

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.stop()
        _binding = null
    }

    // 以下、HistoryContract.View 実装
    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showHistoryList(historyList: List<News>) {
        TODO("Not yet implemented")
    }

    override fun transitNewsSite(url: Uri) {
        TODO("Not yet implemented")
    }

    override fun showNoneHistory() {
        TODO("Not yet implemented")
    }

    override fun showToDeleteHistory() {
        TODO("Not yet implemented")
    }

    override fun showError(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun showErrorToast(throwable: Throwable) {
        TODO("Not yet implemented")
    }
    // 以下、HistoryContract.View 実装ここまで
}