package com.example.hackernewschecker.usecase.repository.api

import com.example.hackernewschecker.usecase.domain.News
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Hacker News API と通信する
 */
interface HackerNewsApi {
    @GET("topstories.json")
    suspend fun loadCurrentNewsIdList(): List<Int>

    @GET("item/{newsId}.json")
    suspend fun loadNews(@Path("newsId") newsId: Int): News
}