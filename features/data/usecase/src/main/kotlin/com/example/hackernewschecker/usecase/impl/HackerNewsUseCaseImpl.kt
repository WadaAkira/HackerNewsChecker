package com.example.hackernewschecker.usecase.impl

import com.example.hackernewschecker.repository.Repository
import com.example.hackernewschecker.usecase.HackerNewsUseCase
import javax.inject.Inject

class HackerNewsUseCaseImpl @Inject constructor(private val repository: Repository) :
    HackerNewsUseCase {
    override suspend fun loadCurrentNewsIdList(): List<Int> {
        return repository.loadCurrentNewsIdList()
    }

    override suspend fun loadNews(newsId: Int): com.example.dto.News {
        return repository.loadNews(newsId)
    }
}