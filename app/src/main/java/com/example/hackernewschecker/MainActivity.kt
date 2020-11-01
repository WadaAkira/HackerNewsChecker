package com.example.hackernewschecker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * HackerNewsChecker のローンチアクティビティ<br>
 * メイン画面と履歴画面はフラグメントを切り替えて表示する<br>
 *
 * WebView は別アクティビティとして実装する
 */
class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}