package com.example.hackernewschecker.usecase.impl

import com.example.hackernewschecker.dto.News
import com.example.hackernewschecker.repository.Repository
import com.example.hackernewschecker.usecase.HistoryUseCase
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HistoryUseCaseImpl @Inject constructor(
    private val repository: Repository,
    private val ioDispatcher: CoroutineDispatcher
) :
    HistoryUseCase, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = ioDispatcher

    override suspend fun insert(news: News) {
        coroutineScope {
            launch(ioDispatcher) {
                repository.insertNews(news)
            }
        }
    }

    override suspend fun loadList(): List<News> {
        return withContext(ioDispatcher) {
            repository.loadHistoryList()
        }
    }

    override suspend fun delete(news: News) {
        coroutineScope {
            launch(ioDispatcher) {
                repository.deleteHistory(news)
            }
        }
    }
}