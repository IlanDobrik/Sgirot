package com.example.sgirot

import android.content.Context
import android.widget.Toast
import java.net.Socket
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter

const val VERSION = "V0.1"

// General Settings
const val ROWS_COUNT = 6
const val DEFAULT_VALUE = false
const val FILE_NAME = "Sgirot.bin"

// Formats
const val DATE_FORMAT = "dd.MM"
const val REPORT_WEEKSLEFT_FORMAT = "In %d weeks!"
const val REPORT_THIS_WEEK = "This weekend!"
const val REPORT_USERS = "%s\n"
const val NOT_REPORTED = "????"

// Network Settings
const val IP = "10.0.2.2"
const val PORT = 2513
enum class NetworkCommands(string: String){
    GET("GET"),
    POST("POST")
}

// Utilities
fun getWeekends() = sequence {
    val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
    var date = java.time.LocalDate.now()
    date = date.plusDays((DayOfWeek.values().size - date.dayOfWeek.value - 1).toLong())

    while (true)
    {
        val weekend = "%s - %s".format(date.minusDays(2).format(dateFormatter),
            date.format(dateFormatter))
        yield(weekend)
        date = date.plusDays(DayOfWeek.values().size.toLong())
    }
}

fun sendData(byteArray: ByteArray) {
    try {
        val socket = Socket(IP, PORT)
        socket.getOutputStream().write(NetworkCommands.POST.toString().toByteArray())
        socket.getOutputStream().write(byteArray)
    }
    catch(e: Exception) { }
}

fun recvData() : ByteArray? {
    try{
        val socket = Socket(IP, PORT)
        socket.getOutputStream().write(NetworkCommands.GET.toString().toByteArray())
        return socket.getInputStream().readBytes()
    }
    catch(e: Exception) { }
    return null
}

fun toast(context: Context, text : CharSequence, duration : Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, text, duration).show()
}