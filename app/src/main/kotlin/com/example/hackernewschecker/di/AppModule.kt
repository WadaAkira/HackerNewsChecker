package com.example.hackernewschecker.di

import android.app.Application
import androidx.room.Room
import com.example.hackernewschecker.BuildConfig
import com.example.hackernewschecker.history.HistoryContract
import com.example.hackernewschecker.history.HistoryPresenter
import com.example.hackernewschecker.main.MainContract
import com.example.hackernewschecker.main.MainPresenter
import com.example.hackernewschecker.repository.Repository
import com.example.hackernewschecker.repository.database.HackerNewsDatabase
import com.example.hackernewschecker.repository.impl.RepositoryImpl
import com.example.hackernewschecker.usecase.HackerNewsUseCase
import com.example.hackernewschecker.usecase.HistoryUseCase
import com.example.hackernewschecker.usecase.impl.HackerNewsUseCaseImpl
import com.example.hackernewschecker.usecase.impl.HistoryUseCaseImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
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
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val HACKER_NEWS_API_BASE_URL = "https://hacker-news.firebaseio.com/v0/"
    private const val TIMEOUT_SECOND = 30L
    private const val DATABASE_NAME = "hacker_news_database"

    @Singleton
    @Provides
    fun provideDatabase(context: Application): HackerNewsDatabase {
        return Room.databaseBuilder(
            context,
            HackerNewsDatabase::class.java,
            DATABASE_NAME
        ).build()
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
    fun provideHistoryUseCase(repository: Repository): HistoryUseCase {
        return HistoryUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideMainPresenter(presenter: MainPresenter): MainContract.Presenter {
        return presenter
    }

    @Singleton
    @Provides
    fun provideHistoryPresenter(presenter: HistoryPresenter): HistoryContract.Presenter {
        return presenter
    }

    @Singleton
    @Provides
    fun provideMainDispatcher(): MainCoroutineDispatcher {
        return Dispatchers.Main
    }

    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    // okHttpClient を作成する
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .connectTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = if (BuildConfig.DEBUG) {
                    // debuggable の時のみ詳細ログを出力する
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }
}