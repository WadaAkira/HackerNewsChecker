package com.example.repository.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

/**
 * repository モジュールで利用する Database/API 共通のデータクラス
 */
@Entity(tableName = "hacker_news")
data class RepositoryNews(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val by: String?,
    val descendants: Int?,
    @TypeConverters(IntListTypeConverter::class) val kids: List<Int>?,
    val score: Int?,
    val time: Int?,
    val title: String?,
    val type: String?,
    val url: String?
)