package com.example.hackernewschecker.common.view

import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewschecker.common.R
import com.example.hackernewschecker.common.databinding.CardViewholderBinding
import com.example.hackernewschecker.dto.News

/**
 * Hacker News 一つ分の表示
 */
class CardViewHolder(private val binding: CardViewholderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(news: News, callback: (News) -> Unit) {
        // 表示を制御
        val context = itemView.context
        binding.also {
            it.title.text = news.title
            it.author.text = news.by
            it.points.text = context.getString(R.string.score, news.score)
            it.comments.text = context.getString(R.string.comments, news.descendants ?: 0)

            // コールバックの設定
            it.root.setOnClickListener {
                callback(news)
            }
        }
    }
}