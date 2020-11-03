package com.example.hackernewschecker.usecase.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * データベースの１レコードを示すデータ
 */
@Entity(tableName = "hacker_news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "by") val by: String?,
    @ColumnInfo(name = "descendants") val descendants: Int?,
    @ColumnInfo(name = "score") val score: Int?,
    @ColumnInfo(name = "time") val time: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "url") val url: String?
)