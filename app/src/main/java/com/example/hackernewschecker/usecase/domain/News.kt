package com.example.hackernewschecker.usecase.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.hackernewschecker.usecase.repository.database.IntListTypeConverter

/**
 * Hacker News API と通信した時のレスポンスを保持するクラス<br>
 * また、データベースの１レコード分のデータも示す
 */
@Entity(tableName = "hacker_news")
data class News(
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