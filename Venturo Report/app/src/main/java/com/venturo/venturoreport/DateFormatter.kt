package com.venturo.venturoreport

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    fun formatDateString(dateString: String?, outputFormat: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
            outputFormatter.format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun millisecondsToDate(milliseconds: Long, dateFormat: String): String {
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        return sdf.format(Date(milliseconds))
    }
}