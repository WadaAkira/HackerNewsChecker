package com.example.hackernewschecker.usecase.impl

import com.example.hackernewschecker.usecase.HackerNewsUseCase
import com.example.hackernewschecker.usecase.repository.Repository
import com.example.hackernewschecker.usecase.domain.News
import javax.inject.Inject

class HackerNewsUseCaseImpl @Inject constructor(private val repository: Repository) :
    HackerNewsUseCase {
    override suspend fun loadCurrentNewsIdList(): List<Int> {
        return repository.loadCurrentNewsIdList()
    }

    override suspend fun loadNews(newsId: Int): News {
        return repository.loadNews(newsId)
    }
}