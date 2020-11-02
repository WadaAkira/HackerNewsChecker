package com.example.hackernewschecker.usecase.impl

import com.example.hackernewschecker.usecase.HackerNewsUseCase
import com.example.hackernewschecker.usecase.repository.Repository
import com.example.hackernewschecker.usecase.response.News
import retrofit2.Call
import javax.inject.Inject

class HackerNewsUseCaseImpl @Inject constructor(private val repository: Repository) :
    HackerNewsUseCase {
    override suspend fun loadCurrentNewsIdList(): Call<List<Int>> {
        return repository.loadCurrentNewsIdList()
    }

    override suspend fun loadNews(newsId: Int): News {
        return repository.loadNews(newsId)
    }
}