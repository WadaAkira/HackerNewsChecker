package com.example.hackernewschecker.usecase.repository

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
}