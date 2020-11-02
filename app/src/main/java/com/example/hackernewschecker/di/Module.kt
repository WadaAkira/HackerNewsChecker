package com.example.hackernewschecker.di

import com.example.hackernewschecker.usecase.HackerNewsUseCase
import com.example.hackernewschecker.usecase.impl.HackerNewsUseCaseImpl
import com.example.hackernewschecker.usecase.repository.Repository
import com.example.hackernewschecker.usecase.repository.impl.RepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * DI するクラスを定義する
 */
@Module
class Module {
    companion object {
        private const val HACKER_NEWS_API_BASE_URL = "https://hacker-news.firebaseio.com/v0/"
        private const val TIMEOUT_SECOND = 30L
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(HACKER_NEWS_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(createOkHttpClient())
            .build()
    }

    @Singleton
    @Provides
    fun provideRepository(retrofit: Retrofit): Repository {
        return RepositoryImpl(retrofit)
    }

    @Singleton
    @Provides
    fun provideHackerNewsUseCase(repository: Repository): HackerNewsUseCase {
        return HackerNewsUseCaseImpl(repository)
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