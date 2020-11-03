package com.example.hackernewschecker.usecase.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hackernewschecker.usecase.domain.News

/**
 * データベースを管理するクラス
 */
@Database(entities = [News::class], version = 1)
@TypeConverters(IntListTypeConverter::class)
abstract class HackerNewsDatabase : RoomDatabase() {
    abstract fun hackerNewsDao(): HackerNewsDao
}