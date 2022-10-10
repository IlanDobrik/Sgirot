package com.example.sgirot

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
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
            DB.getInstance(this).addDate(localDate, if (!button.isChecked) Sgira.Base else Sgira.Home )
        }

        button.textOff = "Base"
        button.textOn = "Home"
        button.isChecked = defaultValue

        if(DB.getInstance(this).data["Info"]?.contains(localDate.toString()) == true)
        {
            button.isChecked = DB.getInstance(this).data["Info"]?.get(localDate.toString()) == "Home"
        }


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
        DB.getInstance(this).save()
        // TODO send data
    }

    fun settingsClick()
    {
        // TODO settings page - Low priority
    }

    fun nameChange()
    {
        // TODO update DB
    }
}
