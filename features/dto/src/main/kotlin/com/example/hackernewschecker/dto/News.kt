package com.example.hackernewschecker.dto

/**
 * １記事分の Hacker News の情報を保持するデータクラス
 */
data class News(
    val id: Int,
    val by: String?,
    val descendants: Int?,
    val kids: List<Int>?,
    val score: Int?,
    val time: Int?,
    val title: String?,
    val type: String?,
    val url: String?
)