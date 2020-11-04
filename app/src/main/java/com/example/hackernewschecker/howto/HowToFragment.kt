package com.example.hackernewschecker.howto

import androidx.fragment.app.Fragment

/**
 * 使い方を表示するフラグメント
 */
class HowToFragment : Fragment() {
    companion object {
        /**
         * フラグメントを生成する
         */
        fun newInstance(): HowToFragment {
            return HowToFragment()
        }
    }
}