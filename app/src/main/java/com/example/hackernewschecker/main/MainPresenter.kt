package com.example.hackernewschecker.main

import com.example.hackernewschecker.usecase.HackerNewsUseCase
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainPresenter @Inject constructor(private val useCase: HackerNewsUseCase) :
    MainContract.Presenter,
    CoroutineScope {

    companion object {
        private const val CURRENT_NEWS_TAKE_VALUE = 30
    }

    private lateinit var view: MainContract.View

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
        val job = launch(exceptionHandler) {
            view.showLoading()

            withContext(Dispatchers.IO) {
                val newsIdList = useCase.loadCurrentNewsIdList()

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

    // Hacker News Id にひもづく記事を取得する
    private fun loadNews(newsIdList: List<Int>) {
        val job = launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                val responseList = newsIdList.map { newsId ->
                    async { useCase.loadNews(newsId) }
                }.awaitAll()

                withContext(Dispatchers.Main) {
                    view.hideLoading()
                    view.showNewsList(responseList)
                }
            }
        }
        jobList.add(job)
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