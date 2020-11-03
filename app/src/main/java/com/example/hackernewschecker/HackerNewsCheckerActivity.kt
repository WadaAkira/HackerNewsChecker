package com.example.hackernewschecker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.hackernewschecker.databinding.HackerNewsCheckerActivityBinding
import com.example.hackernewschecker.main.MainFragment

/**
 * HackerNewsChecker のローンチアクティビティ<br>
 * メイン画面、履歴画面、ライセンス画面はフラグメントを切り替えて表示する
 */
class HackerNewsCheckerActivity : AppCompatActivity() {
    private lateinit var binding: HackerNewsCheckerActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HackerNewsCheckerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar の設定
        binding.toolbar.title = getString(R.string.app_name)
        setSupportActionBar(binding.toolbar)

        // MainFragment の設定
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu ?: return false
        menuInflater.inflate(R.menu.hacker_news_checker_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.reader -> Log.d("wada", "menu tapped.")
        }
        return false
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