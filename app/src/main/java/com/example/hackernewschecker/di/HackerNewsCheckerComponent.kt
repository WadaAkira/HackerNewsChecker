package com.example.hackernewschecker.di

import com.example.hackernewschecker.history.HistoryFragment
import com.example.hackernewschecker.main.MainFragment
import javax.inject.Singleton

/**
 * DI を実行するクラスを定義する
 */
@Singleton
@dagger.Component(
    modules = [HackerNewsCheckerModule::class]
)
interface HackerNewsCheckerComponent {
    fun inject(fragment: MainFragment)
    fun inject(fragment: HistoryFragment)
}