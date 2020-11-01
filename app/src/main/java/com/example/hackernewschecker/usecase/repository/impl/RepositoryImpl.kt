package com.example.hackernewschecker.usecase.repository.impl

import com.example.hackernewschecker.usecase.repository.Repository
import com.example.hackernewschecker.usecase.repository.api.HackerNewsApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RepositoryImpl():Repository {
    companion object {
        private const val HACKER_NEWS_API_BASE_URL = "https://hacker-news.firebaseio.com/v0"
        private const val TIMEOUT_SECOND = 30L
    }

    private var retrofit: Retrofit

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(HACKER_NEWS_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(createOkHttpClient())
            .build()
    }

    override suspend fun loadCurrentNewsIdList(): Response<List<Int>> {
        return retrofit.create(HackerNewsApi::class.java).loadCurrentNewsIdList().execute()
    }

    // okHttpClient を作成する
    private fun createOkHttpClient(): OkHttpClient {
        // Todo リリース版ではログが出力されないように制御
        return OkHttpClient()
            .newBuilder()
            .connectTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}