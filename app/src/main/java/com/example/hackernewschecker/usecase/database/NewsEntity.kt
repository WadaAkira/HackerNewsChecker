package com.example.hackernewschecker.usecase.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * データベースの１レコードを示すデータ
 */
@Entity(tableName = "hacker_news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val by: String?,
    val descendants: Int?,
    val score: Int?,
    val time: Int?,
    val title: String?,
    val type: String?,
    val url: String?
)