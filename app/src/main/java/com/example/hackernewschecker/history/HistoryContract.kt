package com.example.hackernewschecker.history

import android.net.Uri
import com.example.hackernewschecker.usecase.domain.News

/**
 * 履歴画面の実装規約
 */
interface HistoryContract {
    /**
     * 履歴画面の仕様
     */
    interface Presenter {
        /**
         * プレゼンターをセットアップする
         *
         * @param view 表示規約
         */
        fun setup(view: View)

        /**
         * 履歴画面に表示する情報を読み込み表示する
         */
        fun loadPage()

        /**
         * Hacker News の詳細ページを開く
         *
         * @param news 開く Hacker News の情報
         */
        fun openNewsSite(news: News)

        /**
         * 履歴を削除する
         *
         * @param news 削除する履歴
         */
        fun deleteHistory(news: News)

        /**
         * プレゼンターの処理を解放する
         */
        fun stop()
    }

    /**
     * 履歴画面の表示/画面遷移
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
         * Hacker News の表示履歴を表示する
         *
         * @param historyList 表示する Hacker News のリスト
         */
        fun showHistoryList(historyList: List<News>)

        /**
         * Hacker News の対象ページを表示する
         *
         * @param url 表示する Web サイトの URL
         */
        fun transitNewsSite(url: Uri)

        /**
         * まだ履歴がないことを表示する
         */
        fun showNoneHistory()

        /**
         * 履歴の削除を表示に反映する
         *
         * @param newsId 削除する履歴の ID
         * @param title 削除する履歴のタイトル
         */
        fun showToDeleteHistory(newsId: Int, title: String)

        /**
         * 履歴の取得に失敗したことを表示する
         *
         * @param throwable 例外
         */
        fun showError(throwable: Throwable)

        /**
         * 外部ブラウザの表示に失敗したことをトーストで通知する
         *
         * @param throwable 例外
         */
        fun showErrorToast(throwable: Throwable)
    }
}