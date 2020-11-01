package com.example.hackernewschecker.usecase.repository.impl

import com.example.hackernewschecker.usecase.repository.Repository
import okhttp3.OkHttpClient

class RepositoryImpl():Repository {
    private val okHttpClient = OkHttpClient.Builder().build()

    override suspend fun loadCurrentNewsIdList(): List<Int> {
        // https://hacker-news.firebaseio.com/v0/topstories.json
        TODO("Not yet implemented")
    }
}