package com.example.hackernewschecker.usecase.impl

import com.example.hackernewschecker.usecase.HackerNewsUseCase
import com.example.hackernewschecker.usecase.domain.News
import com.example.hackernewschecker.usecase.repository.Repository
import javax.inject.Inject

class HackerNewsUseCaseImpl @Inject constructor(private val repository: Repository) :
    HackerNewsUseCase {
    override suspend fun loadCurrentNewsIdList(): List<Int> {
        return repository.loadCurrentNewsIdList()
    }

    override suspend fun loadNews(newsId: Int): News {
        return repository.loadNews(newsId)
    }

    override suspend fun insertNews(news: News) {
        repository.insertNews(news)
    }
}