package com.example.sgirot

import android.content.Context
import com.google.gson.Gson
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

//data class Data{
//    var info = mutableMapOf<Any,Any>()
//    var settings = mutableMapOf<Any, Any>()
//}

class DB private constructor(context: Context) {
    private val file = File(context.filesDir, "Sgirot.bin")
    public var data = mutableMapOf("Info" to mutableMapOf<Any,Any>(),
        "Settings" to mutableMapOf<Any,Any>())

    init {
        val x = file.createNewFile()
        if (!x){
            val gson = Gson()
            val dataType = object : TypeToken<MutableMap<String, MutableMap<Any, Any>>>() {}.type
            data = gson.fromJson(file.readText(), dataType)
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

    fun addDate(localDate: LocalDate, sgira: Sgira)
    {
        data["Info"]?.put(localDate.toString(), sgira.toString())
    }

    fun save()
    {
        val gson = Gson()
        file.writeText(gson.toJson(this.data))
    }
}

