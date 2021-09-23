package com.example.hackernewschecker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hackernewschecker.databinding.AppActivityBinding
import com.example.hackernewschecker.history.HistoryFragment
import com.example.hackernewschecker.main.MainFragment
import com.example.howto.HowToFragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * HackerNewsChecker のローンチアクティビティ<br>
 * メイン画面、履歴画面、ライセンス画面はフラグメントを切り替えて表示する
 */
@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    companion object {
        private const val OFFICIAL_WEB_SITE_URL = "https://news.ycombinator.com/"
    }

    private lateinit var binding: AppActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AppActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar の設定
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
        val fragments = supportFragmentManager.fragments

        // フラグメントの切り替え前後で同じフラグメントに切り替えていないか確認する
        if (fragments.isNotEmpty()) {
            val currentFragment = fragments[0]
            when {
                currentFragment is MainFragment && fragment is MainFragment -> return
                currentFragment is HistoryFragment && fragment is HistoryFragment -> return
                currentFragment is HowToFragment && fragment is HowToFragment -> return
            }
        }

        // 切り替えるフラグメントに応じて、ツールバーの表示を変える
        binding.toolbar.title = getString(
            when (fragment) {
                is HistoryFragment -> R.string.history_name
                is HowToFragment -> R.string.how_to_name
                else -> R.string.app_name
            }
        )

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    // Popup メニューを作成する
    private fun createPopupMenu() {
        val menu = findViewById<View>(R.id.reader)
        PopupMenu(this, menu).also {
            it.menuInflater.inflate(R.menu.hacker_news_checker_popup_menu, it.menu)
            it.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.top -> switchFragment(MainFragment.newInstance())
                    R.id.history -> switchFragment(HistoryFragment.newInstance())
                    R.id.how_to -> switchFragment(HowToFragment.newInstance())
                    R.id.license -> startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                    R.id.official -> startWebBrowser(Uri.parse(OFFICIAL_WEB_SITE_URL))
                }
                true
            }
            it.show()
        }
    }
}