package com.example.hackernewschecker.main

import android.net.Uri
import com.example.hackernewschecker.usecase.HackerNewsUseCase
import com.example.hackernewschecker.usecase.HistoryUseCase
import com.example.hackernewschecker.usecase.domain.News
import com.example.hackernewschecker.util.addTo
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainPresenter @Inject constructor(
    private val newsUseCase: HackerNewsUseCase,
    private val historyUseCase: HistoryUseCase
) :
    MainContract.Presenter,
    CoroutineScope {

    companion object {
        private const val CURRENT_NEWS_TAKE_VALUE = 7
        private const val LOADNEXT_TAKE_VALUE = 15
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
        /*
         * Pull To Refresh を実装したため、ロードファースト/ロードネクスト中に、
         * もう一度ロードファーストが実行される可能性がある。
         * それを防止するためのフラグ制御。
         */
        if (isLoading) {
            return
        }

        // 通信失敗時や Pull To Refresh のリトライに備え、リストを空にしておく
        isLoading = true
        newsIdList = emptyList()
        view.clearNews()

        launch(exceptionHandler) {
            view.showLoading()

            newsIdList = withContext(Dispatchers.IO) {
                newsUseCase.loadCurrentNewsIdList()
            }

            if (newsIdList.isEmpty()) {
                view.hideLoading()
                view.showError(IllegalStateException("Current stories are not found."))
            } else {
                loadNews(newsIdList.take(CURRENT_NEWS_TAKE_VALUE), CURRENT_NEWS_TAKE_VALUE)
            }
        }.addTo(jobList)
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

        launch(exceptionHandler) {
            view.showLoading()
            loadNews(newsIdList.subList(loadNextFirstIndex, lastIndex), lastIndex)
        }.addTo(jobList)
    }

    // Hacker News Id にひもづく記事を取得する
    private suspend fun loadNews(newsIdList: List<Int>, loadNextFirstIndex: Int) {
        val responseList = newsIdList.map { newsId ->
            withContext(Dispatchers.IO) {
                newsUseCase.loadNews(newsId)
            }
        }

        this.loadNextFirstIndex = loadNextFirstIndex
        isLoading = false
        view.hideLoading()
        view.showNewsList(responseList)
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

        // 画面遷移しつつ Database に保存する
        launch(Dispatchers.IO) {
            // stop() 時にキャンセルしないため jobList に追加しない
            historyUseCase.insert(news)
        }

        view.transitNewsSite(uri)
    }

    override fun stop() {
        jobList.forEach {
            it.cancel()
        }
    }
}