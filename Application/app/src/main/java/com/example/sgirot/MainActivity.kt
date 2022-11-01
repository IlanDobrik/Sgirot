package com.example.sgirot

import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.gson.Gson


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        formatActivity()
    }

    override fun onStop() {
        super.onStop()

        // Save data
        val db = DB.getInstance(this)
        db.save()

        // Send data - Cant do on main thread
        // TODO move to function
        // TODO coroutine?
        val thread = Thread {
            try {
                sendData(Gson().toJson(db.data).toByteArray())
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private fun formatActivity()
    {
        val version : TextView = findViewById(R.id.versionText)
        version.text = VERSION

        // Name
        val name : EditText = findViewById(R.id.editTextTextPersonName)
        val savedName = DB.getInstance(this).data.Name
        name.setText(savedName)
        name.doAfterTextChanged {
            DB.getInstance(this).data.Name = name.text.toString()
            // formatActivity() // TODO remake weekendlist
        }

        // Weekend list
        val context = this
        val weekends = getWeekends().take(ROWS_COUNT).toList()
        val listView = findViewById<View>(R.id.weekend_list) as ListView
        listView.adapter = MyAdapter(this, weekends)
        listView.isClickable = true // TODO is needed?
        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val intent = ReportActivity.newIntent(context, weekends[position], position)
                startActivity(intent)
            }
    }

    fun settingsClick()
    {
        // TODO settings page - Low priority
    }
}
