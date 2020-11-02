package com.example.hackernewschecker.usecase.response

/**
 * Hacker News API と通信した時のレスポンスを保持するクラス
 */
data class News(
    val id: Int,
    val by: String,
    val descendants: Int,
    val kids: List<Int>?,
    val score: Int,
    val time: Int,
    val title: String,
    val type: String,
    val url: String
)