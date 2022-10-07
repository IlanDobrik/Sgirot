package com.example.sgirot

import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
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

    private fun homeBaseButton() : ToggleButton
    {
        val button = ToggleButton(this)
        button.textOff = "Base"
        button.textOn = "Home"
        button.isChecked = false // TODO Pull from DB
        return button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add rows
        val table = findViewById<View>(R.id.tableLayout) as TableLayout
        getWeekends().take(rowsCount).forEach {
            val row = TableRow(this)
            // TODO Style?
            row.addView(homeBaseButton())
            row.addView(dateText(it))
            table.addView(row)
        }
    }

    fun entryClick()
    {
        // TODO Go to Report page
    }

    fun buttonClick()
    {
        // TODO update DB
    }

    // TODO when to send data? every morning? on app close?
}
