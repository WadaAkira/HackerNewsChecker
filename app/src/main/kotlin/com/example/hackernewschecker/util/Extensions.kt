package com.example.hackernewschecker.util

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Job

/*
 * 拡張関数を定義するファイル
 */

/**
 * トーストを表示する
 *
 * @param resourceId 表示するメッセージのリソースID
 * @param length 表示する長さ
 */
fun Context.showToast(resourceId: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resourceId, length).show()
}

/**
 * トーストを表示する
 *
 * @param msg 表示するメッセージ
 * @param length 表示する長さ
 */
fun Context.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}

/**
 * Job をリストに追加する
 *
 * @param list リスト
 */
fun Job.addTo(list: MutableList<Job>) {
    list.add(this)
}

/**
 * null 文字の場合空文字に変換する。<br>
 * null 以外の文字が設定されていた場合、その文字を返す。
 *
 * @return null 以外の文字
 */
fun String?.toEmptyOrString(): String {
    return this ?: ""
}