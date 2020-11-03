package com.example.hackernewschecker.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewschecker.databinding.MainViewholderBinding
import com.example.hackernewschecker.usecase.domain.News
import javax.inject.Inject

/**
 * 起動時画面の RecyclerView のアダプタ
 */
class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainViewHolder>() {
    private val newsList: MutableList<News> = mutableListOf()

    // Hacker News タップ時のコールバック
    internal var newsCallback: (String) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            MainViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(newsList[position], newsCallback)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun setNewsList(newsList: List<News>) {
        this.newsList += newsList
        notifyDataSetChanged()
    }

    fun clearNewsList() {
        newsList.clear()
        notifyDataSetChanged()
    }
}