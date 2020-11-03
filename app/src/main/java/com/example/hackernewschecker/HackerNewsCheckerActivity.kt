package com.example.hackernewschecker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
        switchFragment(MainFragment.newInstance())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu ?: return false
        menuInflater.inflate(R.menu.hacker_news_checker_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.reader -> createPopupMenu()
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

    // 以下 private メソッド

    // Fragment を切り替える
    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()

        // Todo 同じフラグメントを表示しようとしていないかチェックする処理を実装
        Log.d("wada", supportFragmentManager.fragments.toString())
    }

    // Popup メニューを作成する
    private fun createPopupMenu() {
        val menu = findViewById<View>(R.id.reader)
        PopupMenu(this, menu).also {
            it.menuInflater.inflate(R.menu.hacker_news_checker_popup_menu, it.menu)
            it.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.top -> switchFragment(MainFragment.newInstance())
                    R.id.history -> switchFragment(MainFragment.newInstance())
                    R.id.license -> switchFragment(MainFragment.newInstance())
                }
                true
            }
            it.show()
        }
    }
}