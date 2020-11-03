package com.example.hackernewschecker.main

import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewschecker.R
import com.example.hackernewschecker.databinding.MainViewholderBinding
import com.example.hackernewschecker.usecase.domain.News

/**
 * Hacker News 一つ分の表示
 */
class MainViewHolder(private val binding: MainViewholderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(news: News, callback: (String) -> Unit) {
        // 表示を制御
        val context = itemView.context
        binding.also {
            it.title.text = news.title
            it.author.text = news.by
            it.points.text = context.getString(R.string.score, news.score)
            it.comments.text = context.getString(R.string.comments, news.descendants ?: 0)

            // コールバックの設定
            it.root.setOnClickListener {
                callback(news.url ?: "")
            }
        }
    }
}