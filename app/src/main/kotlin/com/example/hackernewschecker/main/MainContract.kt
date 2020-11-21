package com.example.hackernewschecker.main

import android.net.Uri
import com.example.dto.News

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
         * ロードネクストを実行する
         */
        fun loadNext()

        /**
         * Hacker News の詳細ページを開く
         *
         * @param news 開く Hacker News の情報
         */
        fun openNewsSite(news: News)

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
         * すべての Hacker News を破棄する
         */
        fun clearNews()

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
        fun transitNewsSite(url: Uri)

        /**
         * エラーメッセージを表示する
         *
         * @param throwable 例外
         */
        fun showError(throwable: Throwable)

        /**
         * エラーメッセージをトーストで表示する
         *
         * @param throwable 例外
         */
        fun showErrorToast(throwable: Throwable)
    }
}