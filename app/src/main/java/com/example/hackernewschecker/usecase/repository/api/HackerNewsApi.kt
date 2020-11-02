package com.example.hackernewschecker.usecase.repository.api

import retrofit2.Call
import retrofit2.http.GET

/**
 * Hacker News API と通信する
 */
interface HackerNewsApi {
    @GET("topstories.json")
    fun loadCurrentNewsIdList(): Call<List<Int>>
}