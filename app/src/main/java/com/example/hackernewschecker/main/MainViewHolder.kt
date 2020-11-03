package com.example.hackernewschecker.main

import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewschecker.databinding.MainViewholderBinding
import com.example.hackernewschecker.usecase.response.News

/**
 * Hacker News 一つ分の表示
 */
class MainViewHolder(private val binding: MainViewholderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(news: News, callback: (String) -> Unit) {
        // 表示を制御
        binding.title.text = news.title

        // コールバックの設定
        val url = "https://news.ycombinator.com/item?id=${news.id}"
        binding.root.setOnClickListener {
            callback(url)
        }
    }
}