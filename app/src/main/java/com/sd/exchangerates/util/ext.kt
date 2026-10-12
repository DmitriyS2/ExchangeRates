package com.sd.exchangerates.util

import java.text.DecimalFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun Long.separate(): String = DecimalFormat().format(this).toString()

fun String.separate(): String {
    return try {
        val number = this.toLong()
        number.separate()
    } catch (_: Exception) {
        this
    }
}

fun Double.separate(): String = DecimalFormat().format(this).toString()

fun String.toLocalDate(): String {
    return try {
        val parsed = OffsetDateTime.parse(this)
        parsed.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    } catch (_: Exception) {
        this
    }
}