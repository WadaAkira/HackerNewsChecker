package com.example.dto

import androidx.room.TypeConverter
import com.example.dto.util.Log
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
                Log.e("Could not serialize. ${throwable.message}")
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
                Log.e("Could not deserialize. ${throwable.message}")
                null
            }
        }
    }
}