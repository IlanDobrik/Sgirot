package com.example.sgirot

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.gson.Gson
import java.net.Socket


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Name
        val name : EditText = findViewById(R.id.editTextTextPersonName)
        val savedName = DB.getInstance(this).data.Name
        name.setText(savedName)
        name.doAfterTextChanged {
            DB.getInstance(this).data.Name = name.text.toString()
        }

        // Weekend list
        val listView = findViewById<View>(R.id.weekend_list) as ListView
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Do something in response to the click
        }
        listView.adapter = MyAdapter(this, getWeekends().take(ROWS_COUNT).toList())
    }

    override fun onStop() {
        super.onStop()

        // Save data
        val db = DB.getInstance(this)
        db.save()

        // Send data - Cant do on main thread
        val thread = Thread {
            try {
                sendData(Gson().toJson(db.data).toByteArray())
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    fun settingsClick()
    {
        // TODO settings page - Low priority
    }
}
