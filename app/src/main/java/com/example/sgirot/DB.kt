package com.example.sgirot

import android.content.Context
import com.google.gson.Gson
import java.io.File
import java.net.Socket

/*
{
"Info" : {
    DATE : BASE/HOME
    ...
    },
"Settings" : {
    "DefaultValue" : BASE/HOME,
    "Weekends" : 4, // Number of rows to show
    ...
    }
}
 */

data class Asd(
    var Info: MutableMap<String, Boolean>,
    var Settings: MutableMap<String, Int>,
    var Name: String)

class DB private constructor(context: Context) {
    private val file = File(context.filesDir, FILE_NAME)
    public var data = Asd(mutableMapOf(),
        mutableMapOf(),
        "Name")

    init {
        get_update()
        val x = file.createNewFile()
        if (!x){
            val gson = Gson()
            data = gson.fromJson(file.readText(), Asd::class.java)
        }
    }

    companion object {
        private var instance: DB? = null

        fun getInstance(context: Context): DB {
            if (instance == null)  // NOT thread safe!
                instance = DB(context)

            return instance!!
        }
    }

    fun save()
    {
        val gson = Gson()
        file.writeText(gson.toJson(this.data))
    }

    fun get_update()
    {
        try {
            // TODO pull data
            val socket = Socket(IP, PORT)
            socket.getOutputStream().write(Gson().toJson(this.data).toByteArray())

            // TODO Write to DB
        }
        catch (e: Exception)
        {
            // Do nothing
        }
    }
}
