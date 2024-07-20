package com.sd.exchangerates.utils

fun checkInputText(text: String): Boolean {
    if (text.isEmpty()) return false
    return try {
        val number: Long = text.toLong()
        number > 0
    } catch (e: Exception) {
        false
    }
}