package com.example.hackernewschecker.usecase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * データベースを管理するクラス
 */
@Database(entities = [NewsEntity::class], version = 1)
@TypeConverters(IntListTypeConverter::class)
abstract class HackerNewsDatabase : RoomDatabase() {
    abstract fun hackerNewsDao(): HackerNewsDao
}