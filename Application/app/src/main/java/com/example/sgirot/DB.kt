package com.example.sgirot

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.File

/*
Option 1
[
    (Name, Date, Base/home),
    ..
]


Option 2
{
"Info" : {
    "NAME" : {
        "DATE" : BASE/HOME
        ...
            }
        }
    },
"Settings" : {
    "DefaultValue" : BASE/HOME,
    "Weekends" : 4, // Number of rows to show
    ...
    }
}
*/

/*
Info - Synced with server
Settings - local changes
Name - User name
*/

data class Asd(
    var Info: MutableMap<String, MutableMap<String, Boolean>>,
    var Settings: MutableMap<String, Int>,
    var Name: String)

class DB private constructor(context: Context) {
    private val file = File(context.filesDir, FILE_NAME)
    var data = Asd(mutableMapOf(),
        mutableMapOf(),
        "Name")
    private val gson = Gson()

    init {
        getUpdate()
        file.createNewFile()
        try {
            var temp = gson.fromJson(file.readText(), Asd::class.java)
            // temp = null // Uncomment to reset DB - Close app to empty DB
            if (temp != null)
                data = temp
        }
        catch (e : JsonSyntaxException) {
            // TODO save to debug file / Send file
        }
        }

    // Singleton
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
        file.writeText(gson.toJson(this.data))
    }

    fun getUpdate()
    {
        val buff = recvData()

        // TODO Write to DB
    }
}
