package com.example.hackernewschecker.usecase.repository

import com.example.hackernewschecker.usecase.response.News
import retrofit2.Call

/**
 * アプリのデータアクセスを一元管理する
 */
interface Repository {
    /**
     * 最新の Hacker News の id のリストを取得する
     *
     * @return Hacker News の id のリスト
     */
    suspend fun loadCurrentNewsIdList(): Call<List<Int>>

    /**
     * Hacker News の要約を一件読み込む
     *
     * @param newsId Hacker News の ID
     * @return 読み込んだ Hacker News の情報
     */
    suspend fun loadNews(newsId: Int): News
}