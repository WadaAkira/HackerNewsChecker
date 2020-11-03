package com.example.hackernewschecker.usecase.repository.database

import android.util.Log
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi

/**
 * Room はリストを保存できないため、整数型リストを JSON 文字列に変換する
 */
class IntListTypeConverter {
    @TypeConverter
    fun fromList(list: List<Int>?): String? {
        return list?.let {
            val adapter = Moshi.Builder().build().adapter<List<Int>>(List::class.java)
            try {
                adapter.toJson(it)
            } catch (throwable: Throwable) {
                Log.e("Hacker News Checker", "Could not serialize. ${throwable.message}")
                null
            }
        }
    }

    @TypeConverter
    fun toList(string: String?): List<Int>? {
        return string?.let {
            val adapter = Moshi.Builder().build().adapter<List<Int>>(List::class.java)
            try {
                adapter.fromJson(it)
            } catch (throwable: Throwable) {
                Log.e("Hacker News Checker", "Could not deserialize. ${throwable.message}")
                null
            }
        }
    }
}