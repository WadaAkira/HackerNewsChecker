package com.example.hackernewschecker

import android.app.Application
import com.example.hackernewschecker.di.DaggerHackerNewsCheckerComponent
import com.example.hackernewschecker.di.HackerNewsCheckerComponent
import com.example.hackernewschecker.di.HackerNewsCheckerModule

/**
 * アプリ共通の機能（DI）を提供するアプリケーションクラス
 */
class HackerNewsCheckerApplication : Application() {
    private lateinit var _appComponent: HackerNewsCheckerComponent
    internal val appComponent: HackerNewsCheckerComponent
        get() = _appComponent

    override fun onCreate() {
        super.onCreate()

        // DI Component のセットアップ
        _appComponent = DaggerHackerNewsCheckerComponent.builder()
            .hackerNewsCheckerModule(HackerNewsCheckerModule(this))
            .build()
    }
}