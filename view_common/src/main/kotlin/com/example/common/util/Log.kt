package com.example.common.util

import com.example.common.BuildConfig

/**
 * ロギング用のユーティリティ
 */
object Log {
    private const val TAG = "Hacker News Checker"

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