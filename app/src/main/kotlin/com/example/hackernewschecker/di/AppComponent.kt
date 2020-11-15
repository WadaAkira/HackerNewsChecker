package com.example.hackernewschecker.di

import com.example.hackernewschecker.history.HistoryFragment
import com.example.hackernewschecker.main.MainFragment
import javax.inject.Singleton

/**
 * DI を実行するクラスを定義する<br>
 * DI するオブジェクトは、アプリケーションと同じ生存期間でシングルトンとして管理する
 */
@Singleton
@dagger.Component(
    modules = [AppModule::class]
)
interface AppComponent {
    fun inject(fragment: MainFragment)
    fun inject(fragment: HistoryFragment)
}