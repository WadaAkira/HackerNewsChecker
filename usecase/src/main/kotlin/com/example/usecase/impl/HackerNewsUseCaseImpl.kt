package com.example.usecase.impl

import com.example.usecase.HackerNewsUseCase
import com.example.repository.Repository
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