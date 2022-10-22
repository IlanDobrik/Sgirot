package com.example.sgirot

import java.net.Socket
import java.time.DayOfWeek

// General Settings
const val ROWS_COUNT = 6
const val DEFAULT_VALUE = false
const val FILE_NAME = "Sgirot.bin"

// Network Settings
const val IP = "10.0.2.2"
const val PORT = 2513
enum class NetworkCommands(string: String){
    GET("GET"),
    POST("POST")
}

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

fun sendData(byteArray: ByteArray) {
    val socket = Socket(IP, PORT)
    socket.getOutputStream().write(NetworkCommands.POST.toString().toByteArray())
    socket.getOutputStream().write(byteArray)
}

fun recvData() : ByteArray {
    val socket = Socket(IP, PORT)
    socket.getOutputStream().write(NetworkCommands.GET.toString().toByteArray())
    return socket.getInputStream().readBytes()
}
