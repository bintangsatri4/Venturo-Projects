package com.venturo.venturoreport

import android.icu.text.NumberFormat
import java.util.Locale

fun Int.formatCurrency(): String {
    return try {
        NumberFormat.getNumberInstance(Locale("id", "ID"))
            .format(this)
            .replace(",", ".")
    } catch (e: Exception) {
        "-"
    }
}