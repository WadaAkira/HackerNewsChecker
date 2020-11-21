package com.example.usecase.usecase

import com.example.dto.News

/**
 * HackerNews API と通信するユースケース
 */
interface HackerNewsUseCase {
    /**
     * 最新の Hacker News の id のリストを取得する。
     *
     * @return Hacker News の id のリスト
     */
    suspend fun loadCurrentNewsIdList(): List<Int>

    /**
     * Hacker News の詳細情報を取得する
     *
     * @param newsId Hacker News の id
     * @return id にひもづく記事の詳細
     */
    suspend fun loadNews(newsId: Int): com.example.dto.News
}