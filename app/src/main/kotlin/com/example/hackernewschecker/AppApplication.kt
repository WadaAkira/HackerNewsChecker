package com.example.hackernewschecker

import android.app.Application
import com.example.hackernewschecker.di.AppComponent
import com.example.hackernewschecker.di.AppModule
import com.example.hackernewschecker.di.DaggerAppComponent

/**
 * アプリ共通の機能（DI）を提供するアプリケーションクラス
 */
class AppApplication : Application() {
    private lateinit var _appComponent: AppComponent
    internal val appComponent: AppComponent
        get() = _appComponent

    override fun onCreate() {
        super.onCreate()

        // DI Component のセットアップ
        _appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}