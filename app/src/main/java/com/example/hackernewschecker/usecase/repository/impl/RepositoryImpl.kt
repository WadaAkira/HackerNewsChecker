package com.example.hackernewschecker.usecase.repository.impl

import com.example.hackernewschecker.usecase.repository.Repository
import com.example.hackernewschecker.usecase.repository.api.HackerNewsApi
import com.example.hackernewschecker.usecase.response.News
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val retrofit: Retrofit) : Repository {
    override suspend fun loadCurrentNewsIdList(): Call<List<Int>> {
        return retrofit.create(HackerNewsApi::class.java).loadCurrentNewsIdList()
    }

    override suspend fun loadNews(newsId: Int): News {
        return retrofit.create(HackerNewsApi::class.java).loadNews(newsId)
    }
}