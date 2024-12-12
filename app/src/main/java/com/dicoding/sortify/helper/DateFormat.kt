package com.dicoding.sortify.helper

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormat {
    fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())

        try {
            val date = inputFormat.parse(dateString)
            return outputFormat.format(date ?: Date())
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
    }
}

