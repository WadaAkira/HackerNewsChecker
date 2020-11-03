package com.example.hackernewschecker.usecase.repository.impl

import com.example.hackernewschecker.usecase.domain.News
import com.example.hackernewschecker.usecase.repository.Repository
import com.example.hackernewschecker.usecase.repository.api.HackerNewsApi
import com.example.hackernewschecker.usecase.repository.database.HackerNewsDatabase
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
}