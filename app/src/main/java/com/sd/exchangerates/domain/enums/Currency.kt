package com.sd.exchangerates.domain.enums

enum class Currency(val nominal: Int, val title: String) {
    NONE(0, "None"),
    USD(1, "Доллар США"),
    EUR(1, "Евро"),
    GBP(1, "Фунт стерлингов"),
    VND(10000, "Вьетнамских донгов"),
    TRY(10, "Турецких лир"),
    RSD(100, "Сербских динаров"),
}