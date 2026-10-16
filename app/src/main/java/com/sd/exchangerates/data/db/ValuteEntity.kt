package com.sd.exchangerates.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.util.BASE_DOUBLE
import com.sd.exchangerates.util.BASE_INT
import com.sd.exchangerates.util.BASE_STRING

@Entity
data class ValuteEntity(
    @PrimaryKey
    val id: String = BASE_STRING,
    val code: String = BASE_STRING,
    val nominal: Int = BASE_INT,
    val name: String = BASE_STRING,
    val value: Double = BASE_DOUBLE,
    val previousValue: Double = BASE_DOUBLE,
    val date: String = BASE_STRING,
)

fun ValuteEntity.toValute(): Valute {
    return Valute(
        id = id,
        code = code,
        nominal = nominal,
        name = name,
        value = value,
        previousValue = previousValue,
    )
}

fun Valute.toEntity(date: String): ValuteEntity {
    return ValuteEntity(
        id = id,
        code = code,
        nominal = nominal,
        name = name,
        value = value,
        previousValue = previousValue,
        date = date,
    )
}
