package com.example.usecase.impl

import com.example.usecase.HistoryUseCase
import com.example.repository.Repository
import javax.inject.Inject

class HistoryUseCaseImpl @Inject constructor(private val repository: Repository) :
    HistoryUseCase {
    override suspend fun insert(news: com.example.dto.News) {
        repository.insertNews(news)
    }

    override suspend fun loadList(): List<com.example.dto.News> {
        return repository.loadHistoryList()
    }

    override suspend fun delete(news: com.example.dto.News) {
        repository.deleteHistory(news)
    }
}