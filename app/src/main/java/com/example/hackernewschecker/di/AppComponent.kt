package com.example.hackernewschecker.di

import com.example.hackernewschecker.main.MainFragment
import javax.inject.Singleton

/**
 * DI を実行するクラスを定義する
 */
@Singleton
@dagger.Component(
    modules = [Module::class]
)
interface AppComponent {
    fun inject(fragment: MainFragment)
}