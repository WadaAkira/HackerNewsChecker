package com.example.hackernewschecker.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hackernewschecker.HackerNewsCheckerApplication
import com.example.hackernewschecker.databinding.MainFragmentBinding
import com.example.hackernewschecker.usecase.response.News
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
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.stop()
        _binding = null
    }

    // 以下、View 実装
    override fun showLoading() {
        Log.d("wada", "show loading")
    }

    override fun hideLoading() {
        Log.d("wada", "hide loading")
    }

    override fun showNewsList(newsList: List<News>) {
        Log.d("wada", "show news list")
    }

    override fun transitNewsSite(url: String) {
        Log.d("wada", "transit site")
    }

    override fun showError(throwable: Throwable) {
        Log.e("HackerNewsChecker", "Failed to get hacker news. ${throwable.message}")
    }
    // View 実装ここまで
}