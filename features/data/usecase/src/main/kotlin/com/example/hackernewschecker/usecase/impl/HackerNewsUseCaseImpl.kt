package com.example.hackernewschecker.usecase.impl

import com.example.hackernewschecker.dto.News
import com.example.hackernewschecker.repository.Repository
import com.example.hackernewschecker.usecase.HackerNewsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HackerNewsUseCaseImpl @Inject constructor(
    private val repository: Repository,
    private val ioDispatcher: CoroutineDispatcher
) :
    HackerNewsUseCase {
    override suspend fun loadCurrentNewsIdList(): List<Int> {
        return withContext(ioDispatcher) {
            repository.loadCurrentNewsIdList()
        }
    }

    override suspend fun loadNews(newsId: Int): News {
        return withContext(ioDispatcher) {
            repository.loadNews(newsId)
        }
    }
}