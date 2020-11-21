package com.example.repository.api

import com.example.repository.data.RepositoryNews
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Hacker News API と通信する
 */
interface HackerNewsApi {
    @GET("topstories.json")
    suspend fun loadCurrentNewsIdList(): List<Int>

    @GET("item/{newsId}.json")
    suspend fun loadNews(@Path("newsId") newsId: Int): RepositoryNews
}