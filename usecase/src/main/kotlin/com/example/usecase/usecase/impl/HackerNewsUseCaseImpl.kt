package com.example.usecase.usecase.impl

import com.example.usecase.usecase.HackerNewsUseCase
import com.example.dto.News
import com.example.repository.repository.Repository
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