package com.example.repository.repository.database

import androidx.room.*
import com.example.dto.News

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