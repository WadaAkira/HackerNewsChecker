package com.example.hackernewschecker.main

import com.example.hackernewschecker.usecase.HackerNewsUseCase
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainPresenter @Inject constructor(private val useCase: HackerNewsUseCase) :
    MainContract.Presenter,
    CoroutineScope {

    companion object {
        private const val CURRENT_NEWS_TAKE_VALUE = 5
    }

    private lateinit var view: MainContract.View

    // ロードネクストの制御
    private var newsIdList: List<Int> = emptyList()
    private var loadCount = 0

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

        val job = launch(exceptionHandler) {
            view.showLoading()

            withContext(Dispatchers.IO) {
                newsIdList = useCase.loadCurrentNewsIdList()

                if (newsIdList.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        view.hideLoading()
                        view.showError(IllegalStateException("Current stories are not found."))
                    }
                } else {
                    loadNews(newsIdList.take(CURRENT_NEWS_TAKE_VALUE))
                }
            }
        }
        jobList.add(job)
    }

    override fun loadNext() {
        // ロードネクストできるか判定
        val firstIndex = loadCount * CURRENT_NEWS_TAKE_VALUE
        if (newsIdList.size <= firstIndex) {
            return
        }

        val lastIndex = if (newsIdList.size <= firstIndex + CURRENT_NEWS_TAKE_VALUE) {
            newsIdList.size
        } else {
            firstIndex + CURRENT_NEWS_TAKE_VALUE
        }

        val job = launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                loadNews(newsIdList.subList(firstIndex, lastIndex))
            }
        }
        jobList.add(job)
    }

    // Hacker News Id にひもづく記事を取得する
    private suspend fun loadNews(newsIdList: List<Int>) {
        val responseList = newsIdList.map { newsId ->
            async { useCase.loadNews(newsId) }
        }.awaitAll()

        withContext(Dispatchers.Main) {
            loadCount++
            view.hideLoading()
            view.showNewsList(responseList)
        }
    }

    override fun openNewsSite(url: String) {
        view.transitNewsSite(url)
    }

    override fun stop() {
        jobList.forEach {
            it.cancel()
        }
    }
}