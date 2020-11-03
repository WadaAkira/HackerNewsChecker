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

    }
}