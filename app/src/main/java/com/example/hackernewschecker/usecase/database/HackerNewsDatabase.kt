package com.example.hackernewschecker.usecase.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * データベースを管理するクラス
 */
@Database(entities = [NewsEntity::class], version = 1)
abstract class HackerNewsDatabase : RoomDatabase() {
    abstract fun hackerNewsDao(): HackerNewsDao
}