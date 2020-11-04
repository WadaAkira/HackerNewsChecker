package com.example.hackernewschecker.history

import android.net.Uri
import com.example.hackernewschecker.usecase.HistoryUseCase
import com.example.hackernewschecker.usecase.domain.News
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HistoryPresenter @Inject constructor(private val useCase: HistoryUseCase) :
    HistoryContract.Presenter, CoroutineScope {

    private lateinit var view: HistoryContract.View

    // コルーチンの制御
    private val jobList = mutableListOf<Job>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        view.hideLoading()
        view.showError(throwable)
    }

    // データベース読込中の制御
    private var isLoading = false

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun setup(view: HistoryContract.View) {
        this.view = view
    }

    override fun loadPage() {
//        kokokara実装
//        adapter / viewholder の共通化
//                viewの実装
    }

    override fun openNewsSite(news: News) {
        // 通信中は画面遷移しないようにする
        if (isLoading) {
            return
        }

        val url = news.url
        if (url.isNullOrEmpty()) {
            view.showErrorToast(IllegalStateException("url is empty."))
            return
        }

        val uri = try {
            Uri.parse(url)
        } catch (e: Throwable) {
            view.showErrorToast(e)
            return
        }

        view.transitNewsSite(uri)
    }

    override fun deleteHistory(news: News) {
        val job = launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                useCase.delete(news)
            }
            view.showToDeleteHistory()
        }
        jobList.add(job)
    }

    override fun stop() {
        jobList.forEach {
            it.cancel()
        }
    }
}