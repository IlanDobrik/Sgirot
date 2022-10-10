package com.example.sgirot

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import com.google.gson.Gson
import java.net.Socket
import java.time.LocalDate

import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity()
{
    private fun dateText(localDate: LocalDate) : TextView
    {
        val dateText = TextView(this)
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM")
        dateText.text = "%s - %s".format(localDate.minusDays(2).format(dateFormatter),
            localDate.format(dateFormatter))
        // TODO style?
        return dateText
    }

    private fun homeBaseButton(localDate: LocalDate) : ToggleButton
    {
        val button = ToggleButton(this)
        button.setOnClickListener {
            DB.getInstance(this).data.Info[localDate.toString()] = button.isChecked
        }

        button.textOff = "Base"
        button.textOn = "Home"
        button.isChecked = defaultValue

        val info = DB.getInstance(this).data.Info
        if(info.contains(localDate.toString()))
        {
            button.isChecked = info[localDate.toString()] == true
        }


        return button
    }

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

        // Add rows
        val table = findViewById<View>(R.id.tableLayout) as TableLayout
        getWeekends().take(rowsCount).forEach {
            val row = TableRow(this)
            // TODO Style?

            // Button
            row.addView(homeBaseButton(it))

            // Date
            row.addView(dateText(it))

            // Row
            row.setOnClickListener{
                startActivity(Intent(this, ReportActivity::class.java))
                // intent.putExtra("date", date) // To pass data
            }

            table.addView(row)
        }
    }

    override fun onStop() {
        super.onStop()

        // Save data
        val db = DB.getInstance(this)
        db.save()

        // TODO Send data
//        val socket = Socket("localhost", 1111)
//        socket.getOutputStream().write(Gson().toJson(db.data).toByteArray())
    }

    fun settingsClick()
    {
        // TODO settings page - Low priority
    }
}
