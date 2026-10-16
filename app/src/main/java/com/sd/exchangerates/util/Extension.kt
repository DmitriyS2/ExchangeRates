package com.sd.exchangerates.util

import java.text.DecimalFormat
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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

fun String.toStringAsLocalDate(): String {
    return try {
        val parsed = OffsetDateTime.parse(this)
        parsed.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    } catch (_: Exception) {
        this
    }
}

private val FMT = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ROOT)
fun String.toDate(): LocalDate = LocalDate.parse(this, FMT)