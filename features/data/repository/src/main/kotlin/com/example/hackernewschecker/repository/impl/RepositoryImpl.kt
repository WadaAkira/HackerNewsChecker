package com.example.hackernewschecker.repository.impl

import com.example.dto.News
import com.example.hackernewschecker.repository.Repository
import com.example.hackernewschecker.repository.api.HackerNewsApi
import com.example.hackernewschecker.repository.data.RepositoryNews
import com.example.hackernewschecker.repository.database.HackerNewsDatabase
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val database: HackerNewsDatabase,
    private val retrofit: Retrofit
) : Repository {
    override suspend fun loadCurrentNewsIdList(): List<Int> {
        return retrofit.create(HackerNewsApi::class.java).loadCurrentNewsIdList()
    }

    override suspend fun loadNews(newsId: Int): News {
        val news = retrofit.create(HackerNewsApi::class.java).loadNews(newsId)
        return createNews(news)
    }

    override suspend fun insertNews(news: News) {
        database.hackerNewsDao().insert(createRepositoryNews(news))
    }

    override suspend fun loadHistoryList(): List<News> {
        return database.hackerNewsDao().loadList().map {
            createNews(it)
        }
    }

    override suspend fun deleteHistory(news: News) {
        database.hackerNewsDao().delete(createRepositoryNews(news))
    }

    // DTO 用の News オブジェクトに変換する
    private fun createNews(news: RepositoryNews): News {
        return News(
            id = news.id,
            by = news.by,
            descendants = news.descendants,
            kids = news.kids,
            score = news.score,
            time = news.time,
            title = news.title,
            type = news.type,
            url = news.url
        )
    }

    // Repository 用の News オブジェクトに変換する
    private fun createRepositoryNews(news: News): RepositoryNews {
        return RepositoryNews(
            id = news.id,
            by = news.by,
            descendants = news.descendants,
            kids = news.kids,
            score = news.score,
            time = news.time,
            title = news.title,
            type = news.type,
            url = news.url
        )
    }
}