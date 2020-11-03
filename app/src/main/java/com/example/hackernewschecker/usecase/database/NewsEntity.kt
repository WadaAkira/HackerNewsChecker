package com.example.hackernewschecker.usecase.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

/**
 * データベースの１レコードを示すデータ
 */
@Entity(tableName = "hacker_news")
data class NewsEntity(
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