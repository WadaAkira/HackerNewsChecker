package com.example.hackernewschecker.usecase.domain

/**
 * Hacker News を一つ保持するデータクラス
 */
data class News(
    val id: Int,
    val by: String,
    val score: Int,
    val title: String,
    val url: String
)