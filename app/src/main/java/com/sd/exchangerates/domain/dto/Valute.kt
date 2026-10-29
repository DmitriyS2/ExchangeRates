package com.sd.exchangerates.domain.dto

import com.google.gson.annotations.SerializedName
import com.sd.exchangerates.util.BASE_DOUBLE
import com.sd.exchangerates.util.BASE_INT
import com.sd.exchangerates.util.BASE_STRING

data class Valute(
    @SerializedName("ID")
    val id: String = BASE_STRING,
    @SerializedName("CharCode")
    val code: String = BASE_STRING,
    @SerializedName("Nominal")
    val nominal: Int = BASE_INT,
    @SerializedName("Name")
    val name: String = BASE_STRING,
    @SerializedName("Value")
    val value: Double = BASE_DOUBLE,
    @SerializedName("Previous")
    val previousValue: Double = BASE_DOUBLE
)

val rub: Valute = Valute(
    id = "R01",
    code = "RUB",
    nominal = 1,
    name = "Российский рубль",
    value = 1.0,
    previousValue = 1.0,
)
