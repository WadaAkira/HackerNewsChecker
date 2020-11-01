package com.example.hackernewschecker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hackernewschecker.databinding.MainActivityBinding

/**
 * HackerNewsChecker のローンチアクティビティ<br>
 * メイン画面と履歴画面はフラグメントを切り替えて表示する<br>
 *
 * WebView は別アクティビティとして実装する
 */
class MainActivity: AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}