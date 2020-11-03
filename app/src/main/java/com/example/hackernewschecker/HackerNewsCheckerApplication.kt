package com.example.hackernewschecker

import android.app.Application
import androidx.room.Room
import com.example.hackernewschecker.di.AppComponent
import com.example.hackernewschecker.di.DaggerAppComponent
import com.example.hackernewschecker.di.Module
import com.example.hackernewschecker.usecase.database.HackerNewsDatabase

/**
 * アプリ共通の機能（DI）を提供するアプリケーションクラス
 */
class HackerNewsCheckerApplication : Application() {
    private lateinit var _appComponent: AppComponent
    internal val appComponent: AppComponent
        get() = _appComponent

    private lateinit var _database: HackerNewsDatabase
    internal val database: HackerNewsDatabase
        get() = _database

    override fun onCreate() {
        super.onCreate()

        // DI Component のセットアップ
        _appComponent = DaggerAppComponent.builder()
            .module(Module())
            .build()

        // Database のセットアップ
        _database = Room.databaseBuilder(
            this,
            HackerNewsDatabase::class.java,
            "hacker_news_database"
        ).build()
    }
}