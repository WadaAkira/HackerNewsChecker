package com.example.common.view

import androidx.recyclerview.widget.RecyclerView
import com.example.common.R
import com.example.common.databinding.CardViewholderBinding
import com.example.repository.domain.News

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