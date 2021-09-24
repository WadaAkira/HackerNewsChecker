package com.example.hackernewschecker.repository.database

import androidx.room.*
import com.example.hackernewschecker.repository.data.RepositoryNews

/**
 * hacker_news テーブルへアクセスするクラス
 */
@Dao
interface HackerNewsDao {
    @Query("SELECT * FROM hacker_news")
    fun loadList(): List<RepositoryNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: RepositoryNews)

    @Delete
    fun delete(news: RepositoryNews)
}