package com.example.repository.repository.impl

import com.example.dto.News
import com.example.repository.repository.Repository
import com.example.repository.repository.api.HackerNewsApi
import com.example.repository.repository.database.HackerNewsDatabase
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val database: HackerNewsDatabase,
    private val retrofit: Retrofit
) : Repository {
    override suspend fun loadCurrentNewsIdList(): List<Int> {
        return retrofit.create(HackerNewsApi::class.java).loadCurrentNewsIdList()
    }

    override suspend fun loadNews(newsId: Int): News {
        return retrofit.create(HackerNewsApi::class.java).loadNews(newsId)
    }

    override suspend fun insertNews(news: News) {
        database.hackerNewsDao().insert(news)
    }

    override suspend fun loadHistoryList(): List<News> {
        return database.hackerNewsDao().loadList()
    }

    override suspend fun deleteHistory(news: News) {
        database.hackerNewsDao().delete(news)
    }
}