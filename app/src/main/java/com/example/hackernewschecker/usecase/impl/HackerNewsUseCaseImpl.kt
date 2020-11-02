package com.example.hackernewschecker.usecase.impl

import com.example.hackernewschecker.usecase.HackerNewsUseCase
import com.example.hackernewschecker.usecase.domain.News
import com.example.hackernewschecker.usecase.repository.Repository
import retrofit2.Call
import javax.inject.Inject

class HackerNewsUseCaseImpl @Inject constructor(private val repository: Repository) : HackerNewsUseCase {
    override suspend fun loadCurrentNewsIdList(): Call<List<Int>> {
        return repository.loadCurrentNewsIdList()
    }

    override suspend fun loadNews(newsId: Int): Call<News> {
        TODO()
    }
}