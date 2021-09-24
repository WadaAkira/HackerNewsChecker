package com.example.hackernewschecker.usecase.impl

import com.example.hackernewschecker.dto.News
import com.example.hackernewschecker.repository.Repository
import com.example.hackernewschecker.usecase.HistoryUseCase
import javax.inject.Inject

class HistoryUseCaseImpl @Inject constructor(private val repository: Repository) :
    HistoryUseCase {
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