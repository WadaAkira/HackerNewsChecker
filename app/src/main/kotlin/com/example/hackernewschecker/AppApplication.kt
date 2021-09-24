package com.example.hackernewschecker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * アプリ共通の機能（DI）を提供するアプリケーションクラス
 */
@HiltAndroidApp
class AppApplication : Application()