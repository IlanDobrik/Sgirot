package com.example.sgirot

import java.time.DayOfWeek

// Settings
const val ROWS_COUNT = 6
const val DEFAULT_VALUE = false
const val IP = "10.0.2.2"
const val PORT = 2513
const val FILE_NAME = "Sgirot.bin"

// Utilities
fun getWeekends() = sequence {
    var date = java.time.LocalDate.now()
    date = date.plusDays((DayOfWeek.values().size - date.dayOfWeek.value - 1).toLong())

    while (true)
    {
        yield(date)
        date = date.plusDays(DayOfWeek.values().size.toLong())
    }
}
