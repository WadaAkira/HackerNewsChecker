package com.example.hackernewschecker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hackernewschecker.databinding.MainActivityBinding
import com.example.hackernewschecker.main.MainFragment

/**
 * HackerNewsChecker のローンチアクティビティ<br>
 * メイン画面と履歴画面はフラグメントを切り替えて表示する<br>
 *
 * WebView は別アクティビティとして実装する
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar の設定
        binding.toolbar.title = getString(R.string.app_name)

        // MainFragment の設定
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()
    }

    /**
     * 外部ブラウザを起動する
     *
     * @param url 開く URL
     */
    fun startWebBrowser(url: Uri) {
        startActivity(Intent(Intent.ACTION_VIEW, url))
    }
}