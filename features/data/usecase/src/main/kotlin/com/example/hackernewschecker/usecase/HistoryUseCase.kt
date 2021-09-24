package com.example.hackernewschecker.usecase

/**
 * 表示履歴に係るユースケース
 */
interface HistoryUseCase {
    /**
     * 表示履歴を一件保存する<br>
     * 同じ id の Hacker News は上書きされる
     *
     * @param news 保存する Hacker News
     */
    suspend fun insert(news: com.example.dto.News)

    /**
     * 表示履歴をすべて取得する
     *
     * @return 表示履歴一覧
     */
    suspend fun loadList(): List<com.example.dto.News>

    /**
     * 表示履歴を削除する
     *
     * @param news 削除する履歴
     */
    suspend fun delete(news: com.example.dto.News)
}