package com.example.hackernewschecker.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewschecker.R
import com.example.hackernewschecker.usecase.response.News
import javax.inject.Inject

/**
 * 起動時画面の RecyclerView のアダプタ
 */
class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainViewHolder>() {
    private var newsList: List<News> = emptyList()

    // Hacker News タップ時のコールバック
    internal var newsCallback: (String) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_viewholder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(newsList[position], newsCallback)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun setNewsList(newsList: List<News>) {
        this.newsList = newsList
        notifyDataSetChanged()
    }
}