package com.example.hackernewschecker.util

import kotlinx.coroutines.Job

/*
 * 拡張関数を定義するファイル
 */

/**
 * Job をリストに追加する
 *
 * @param list リスト
 */
fun Job.addTo(list: MutableList<Job>) {
    list.add(this)
}