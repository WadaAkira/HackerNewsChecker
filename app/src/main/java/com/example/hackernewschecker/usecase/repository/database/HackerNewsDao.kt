package com.example.hackernewschecker.usecase.repository.database

import androidx.room.*
import com.example.hackernewschecker.usecase.domain.News

/**
 * hacker_news テーブルへアクセスするクラス
 */
@Dao
interface HackerNewsDao {
    @Query("SELECT * FROM hacker_news")
    fun loadList(): List<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: News)

    @Delete
    fun delete(news: News)
}