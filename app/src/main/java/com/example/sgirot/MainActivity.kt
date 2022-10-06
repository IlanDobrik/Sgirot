package com.example.sgirot
import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    fun getClosestWeekend() : LocalDate
    {
        val date = java.time.LocalDate.now()
        date.plusDays((DayOfWeek.SUNDAY.value - date.dayOfWeek.value).toLong())
        println(date)
        return date
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cancer?

        val table = findViewById<View>(R.id.tableLayout) as TableLayout
        for (i in 0 until 2)
        {
            val row = TableRow(this)
            val button = ToggleButton(this)
            val date = TextView(this)

            row.addView(button)
            row.addView(date)

            button.textOff = "Base"
            button.textOn = "Home"

            date.text = getClosestWeekend().format(DateTimeFormatter.ofPattern("dd.MM"))

            // Calculate dates
            // Make into entries
            table.addView(row, i);
        }
    }

    fun entryClick()
    {
        // Go to Report
    }
}
