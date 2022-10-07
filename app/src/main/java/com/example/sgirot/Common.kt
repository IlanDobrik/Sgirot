package com.example.sgirot

import java.time.DayOfWeek

val rowsCount = 4

fun getWeekends() = sequence {
    var date = java.time.LocalDate.now()
    date = date.plusDays((DayOfWeek.values().size - date.dayOfWeek.value - 1).toLong())
    while (true)
    {
        yield(date)
        date = date.plusDays(DayOfWeek.values().size.toLong())
    }
}
