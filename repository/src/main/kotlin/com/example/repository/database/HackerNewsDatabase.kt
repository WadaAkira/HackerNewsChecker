package com.example.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.repository.data.IntListTypeConverter
import com.example.repository.data.RepositoryNews

/**
 * データベースを管理するクラス
 */
@Database(entities = [RepositoryNews::class], version = 1, exportSchema = false)
@TypeConverters(IntListTypeConverter::class)
abstract class HackerNewsDatabase : RoomDatabase() {
    abstract fun hackerNewsDao(): HackerNewsDao
}