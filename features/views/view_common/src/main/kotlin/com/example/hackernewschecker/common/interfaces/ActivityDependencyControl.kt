package com.example.hackernewschecker.common.interfaces

import android.net.Uri

/**
 * Fragment から Activity の機能にアクセスするための I/F
 */
interface ActivityDependencyControl {
    /**
     * 外部ブラウザを起動する
     *
     * @param url 開くURL
     */
    fun startWebBrowser(url: Uri)
}