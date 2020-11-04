package com.example.hackernewschecker.common.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewschecker.databinding.MainViewholderBinding
import com.example.hackernewschecker.usecase.domain.News
import javax.inject.Inject

/**
 * 起動時画面の RecyclerView のアダプタ
 */
class CardListAdapter @Inject constructor() : RecyclerView.Adapter<CardViewHolder>() {
    private val newsList: MutableList<News> = mutableListOf()

    // Hacker News タップ時のコールバック
    internal var newsCallback: (News) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            MainViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
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