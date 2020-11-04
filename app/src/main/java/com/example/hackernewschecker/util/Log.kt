package com.example.hackernewschecker.util

import com.example.hackernewschecker.BuildConfig

/**
 * ロギング用のユーティリティ
 */
object Log {
    private const val TAG = "Hacker News Checker"

    /**
     * デバッグレベルのログを表示する
     *
     * @param msg 表示するメッセージ
     */
    fun d(msg: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.d(TAG, msg)
        }
    }

    /**
     * エラーレベルのログを表示する
     *
     * @param msg 表示するメッセージ
     */
    fun e(msg: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.e(TAG, msg)
        }
    }
}