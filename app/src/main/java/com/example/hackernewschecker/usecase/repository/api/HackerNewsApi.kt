package com.example.hackernewschecker.usecase.repository.api

import com.example.hackernewschecker.usecase.response.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Hacker News API と通信する
 */
interface HackerNewsApi {
    @GET("topstories.json")
    fun loadCurrentNewsIdList(): Call<List<Int>>

    @GET("item/{newsId}.json")
    suspend fun loadNews(@Path("newsId") newsId: Int): News
}