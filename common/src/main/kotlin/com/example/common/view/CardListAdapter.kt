package com.example.common.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.databinding.CardViewholderBinding
import com.example.repository.domain.News
import javax.inject.Inject

/**
 * 起動時画面の RecyclerView のアダプタ
 */
class CardListAdapter @Inject constructor() : RecyclerView.Adapter<CardViewHolder>() {
    private val newsList: MutableList<News> = mutableListOf()

    // Hacker News タップ時のコールバック
    var newsCallback: (News) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            CardViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(newsList[position], newsCallback)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    /**
     * RecyclerView に表示する Hacker News の情報を登録する
     *
     * @param newsList 表示する Hacker News のリスト
     */
    fun setNewsList(newsList: List<News>) {
        this.newsList += newsList
        notifyDataSetChanged()
    }

    /**
     * 指定したインデックスの News オブジェクトを返す
     *
     * @param index インデックス
     * @return インデックスに対応した News オブジェクト/取得できない場合は null
     */
    fun getNews(index: Int): News? {
        return if (newsList.size <= index) {
            null
        } else {
            newsList[index]
        }
    }

    /**
     * 指定した newsId の Hacker News の情報を破棄する
     *
     * @param newsId 破棄する Hacker News の id
     */
    fun removeNews(newsId: Int) {
        newsList.removeIf { it.id == newsId }
        notifyDataSetChanged()
    }

    /**
     * アダプタが保持している Hacker News の情報をすべて破棄する
     */
    fun clearNewsList() {
        newsList.clear()
        notifyDataSetChanged()
    }
}