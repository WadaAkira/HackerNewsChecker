package com.example.hackernewschecker.main

import android.net.Uri
import com.example.hackernewschecker.usecase.HackerNewsUseCase
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainPresenter @Inject constructor(private val useCase: HackerNewsUseCase) :
    MainContract.Presenter,
    CoroutineScope {

    companion object {
        private const val CURRENT_NEWS_TAKE_VALUE = 5
        private const val LOADNEXT_TAKE_VALUE = 10
    }

    private lateinit var view: MainContract.View

    // ロードネクストの制御
    private var newsIdList: List<Int> = emptyList()
    private var loadNextFirstIndex = 0
    private var isLoading = false

    // コルーチンの制御
    private val jobList = mutableListOf<Job>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        view.hideLoading()
        view.showError(throwable)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun setup(view: MainContract.View) {
        this.view = view
    }

    override fun loadPage() {
        // 通信失敗時のリトライに備え、リストを空にしておく
        newsIdList = emptyList()
        isLoading = true

        val job = launch(exceptionHandler) {
            view.showLoading()

            newsIdList = withContext(Dispatchers.IO) {
                useCase.loadCurrentNewsIdList()
            }

            if (newsIdList.isEmpty()) {
                view.hideLoading()
                view.showError(IllegalStateException("Current stories are not found."))
            } else {
                loadNews(newsIdList.take(CURRENT_NEWS_TAKE_VALUE), CURRENT_NEWS_TAKE_VALUE)
            }
        }
        jobList.add(job)
    }

    override fun loadNext() {
        // ロードネクストできるか判定
        if (isLoading) {
            return
        }

        if (newsIdList.size <= loadNextFirstIndex) {
            return
        }

        isLoading = true

        val lastIndex = if (newsIdList.size <= loadNextFirstIndex + LOADNEXT_TAKE_VALUE) {
            newsIdList.size
        } else {
            loadNextFirstIndex + LOADNEXT_TAKE_VALUE
        }

        val job = launch(exceptionHandler) {
            view.showLoading()
            loadNews(newsIdList.subList(loadNextFirstIndex, lastIndex), lastIndex)
        }
        jobList.add(job)
    }

    // Hacker News Id にひもづく記事を取得する
    private suspend fun loadNews(newsIdList: List<Int>, loadNextFirstIndex: Int) {
        val responseList = newsIdList.map { newsId ->
            withContext(Dispatchers.IO) {
                useCase.loadNews(newsId)
            }
        }

        this.loadNextFirstIndex = loadNextFirstIndex
        isLoading = false
        view.hideLoading()
        view.showNewsList(responseList)
    }

    override fun openNewsSite(url: String) {
        // 通信中は画面遷移しないようにする
        if (isLoading) {
            return
        }

        if (url.isEmpty()) {
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

    override fun stop() {
        jobList.forEach {
            it.cancel()
        }
    }
}