package com.example.repository.repository

import com.example.dto.News

/**
 * アプリのデータアクセスを一元管理する
 */
interface Repository {
    /**
     * 最新の Hacker News の id のリストを取得する
     *
     * @return Hacker News の id のリスト
     */
    suspend fun loadCurrentNewsIdList(): List<Int>

    /**
     * Hacker News の要約を一件読み込む
     *
     * @param newsId Hacker News の ID
     * @return 読み込んだ Hacker News の情報
     */
    suspend fun loadNews(newsId: Int): News

    /**
     * Hacker News を一件保存する<br>
     * 同じ id の Hacker News は上書きされる
     *
     * @param news 保存する Hacker News
     */
    suspend fun insertNews(news: News)

    /**
     * Hacker News の表示履歴をすべて取得する
     *
     * @return 表示履歴
     */
    suspend fun loadHistoryList(): List<News>

    /**
     * Hacker News の表示履歴を削除する
     *
     * @param news 削除する履歴
     */
    suspend fun deleteHistory(news: News)
}