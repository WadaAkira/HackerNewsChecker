package com.example.hackernewschecker.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewschecker.usecase.response.News

/**
 * Hacker News 一つ分の表示
 */
class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(news: News, callback: (String) -> Unit) {

    }
}