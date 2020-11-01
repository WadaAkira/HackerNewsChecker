package com.example.hackernewschecker.main

import androidx.fragment.app.Fragment

/**
 * Hacker News API と通信し、結果を表示するフラグメント
 */
class MainFragment: Fragment() {
    companion object {
        /**
         * フラグメントを生成する
         */
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}