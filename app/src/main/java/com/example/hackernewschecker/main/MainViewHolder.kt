package com.example.hackernewschecker.main

import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewschecker.R
import com.example.hackernewschecker.databinding.MainViewholderBinding
import com.example.hackernewschecker.usecase.response.News

/**
 * Hacker News 一つ分の表示
 */
class MainViewHolder(private val binding: MainViewholderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(news: News, callback: (String) -> Unit) {
        // 表示を制御
        val context = itemView.context
        binding.title.text = news.title
        binding.author.text = news.by
        binding.points.text = context.getString(R.string.score, news.score)
        binding.comments.text = context.getString(R.string.comments, news.descendants)

        // コールバックの設定
        binding.root.setOnClickListener {
            callback(news.url ?: "")
        }
    }
}