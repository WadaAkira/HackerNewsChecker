package com.example.hackernewschecker.di

import android.content.Context
import androidx.room.Room
import com.example.hackernewschecker.main.MainContract
import com.example.hackernewschecker.main.MainPresenter
import com.example.hackernewschecker.usecase.HackerNewsUseCase
import com.example.hackernewschecker.usecase.impl.HackerNewsUseCaseImpl
import com.example.hackernewschecker.usecase.repository.Repository
import com.example.hackernewschecker.usecase.repository.database.HackerNewsDatabase
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
class HackerNewsCheckerModule(applicationContext: Context) {
    companion object {
        private const val HACKER_NEWS_API_BASE_URL = "https://hacker-news.firebaseio.com/v0/"
        private const val TIMEOUT_SECOND = 30L
        private const val DATABASE_NAME = "hacker_news_database"
    }

    private val database = Room.databaseBuilder(
        applicationContext,
        HackerNewsDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDatabase(): HackerNewsDatabase {
        return database
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
    fun provideRepository(database: HackerNewsDatabase, retrofit: Retrofit): Repository {
        return RepositoryImpl(database, retrofit)
    }

    @Singleton
    @Provides
    fun provideHackerNewsUseCase(repository: Repository): HackerNewsUseCase {
        return HackerNewsUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideMainPresenter(presenter: MainPresenter): MainContract.Presenter {
        return presenter
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