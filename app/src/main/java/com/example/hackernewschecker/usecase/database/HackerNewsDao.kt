package com.example.hackernewschecker.usecase.database

import androidx.room.*

/**
 * hacker_news テーブルへアクセスするクラス
 */
@Dao
interface HackerNewsDao {
    @Query("SELECT * FROM hacker_news")
    fun loadList(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: NewsEntity)

    @Delete
    fun delete(news: NewsEntity)
}