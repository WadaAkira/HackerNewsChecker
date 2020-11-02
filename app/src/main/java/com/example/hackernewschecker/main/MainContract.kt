package com.example.hackernewschecker.main

import com.example.hackernewschecker.usecase.domain.News

/**
 * 起動画面の実装規約
 */
interface MainContract {
    /**
     * 起動画面の仕様
     */
    interface Presenter {
        /**
         * プレゼンターをセットアップする
         *
         * @param view 表示規約
         */
        fun setup(view: View)

        /**
         * 起動画面に表示する情報を読み込み表示する
         */
        fun loadPage()

        /**
         * Hacker News の詳細ページを開く
         */
        fun openNewsSite(url: String)

        /**
         * プレゼンターの処理を解放する
         */
        fun stop()
    }

    /**
     * 起動画面の表示/画面遷移
     */
    interface View {
        /**
         * ローディングを表示する
         */
        fun showLoading()

        /**
         * ローディングを非表示化する
         */
        fun hideLoading()

        /**
         * Hacker News を表示する
         *
         * @param newsList 表示する Hacker News のリスト
         */
        fun showNewsList(newsList: List<News>)

        /**
         * Hacker News の対象ページを表示する
         *
         * @param url 表示する Web サイトの URL
         */
        fun transitNewsSite(url: String)
    }
}