package edu.ucne.leodanny_maria_ap2_p2.data.di

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {
    private val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    @ToJson
    fun toJson(date: Date): String = df.format(date)

    @FromJson
    fun fromJson(value: String): Date? = try {
        df.parse(value)
    } catch (e: Exception) {
        null
    }
}