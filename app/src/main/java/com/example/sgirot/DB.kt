package com.example.sgirot

import android.content.Context
import android.provider.Settings
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.File
import java.time.LocalDate
import kotlin.reflect.typeOf

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
    private val file = File(context.filesDir, "Sgirot.bin")
    public var data = Asd(mutableMapOf(),
        mutableMapOf(),
        "Name")

    init {
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
}

