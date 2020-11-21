package com.example.hackernewschecker.usecase.impl

import com.example.hackernewschecker.usecase.HistoryUseCase
import com.example.repository.domain.News
import com.example.repository.repository.Repository
import javax.inject.Inject

class HistoryUseCaseImpl @Inject constructor(private val repository: Repository) : HistoryUseCase {
    override suspend fun insert(news: News) {
        repository.insertNews(news)
    }

    override suspend fun loadList(): List<News> {
        return repository.loadHistoryList()
    }

    override suspend fun delete(news: News) {
        repository.deleteHistory(news)
    }
}