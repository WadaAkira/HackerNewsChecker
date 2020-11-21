package com.example.repository.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.repository.domain.News

/**
 * データベースを管理するクラス
 */
@Database(entities = [News::class], version = 1, exportSchema = false)
@TypeConverters(IntListTypeConverter::class)
abstract class HackerNewsDatabase : RoomDatabase() {
    abstract fun hackerNewsDao(): HackerNewsDao
}