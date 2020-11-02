package com.example.hackernewschecker.usecase.repository.impl

import com.example.hackernewschecker.usecase.repository.Repository
import com.example.hackernewschecker.usecase.repository.api.HackerNewsApi
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val retrofit: Retrofit) : Repository {
    override suspend fun loadCurrentNewsIdList(): Call<List<Int>> {
        return retrofit.create(HackerNewsApi::class.java).loadCurrentNewsIdList()
    }
}