package com.example.sgirot

import java.time.DayOfWeek

public enum class Sgira(boolean: Boolean){
    Base(false),
    Home(true)
}

// TODO add to settings
val rowsCount = 4
val defaultValue = false

fun getWeekends() = sequence {
    var date = java.time.LocalDate.now()
    date = date.plusDays((DayOfWeek.values().size - date.dayOfWeek.value - 1).toLong())

    while (true)
    {
        yield(date)
        date = date.plusDays(DayOfWeek.values().size.toLong())
    }
}
