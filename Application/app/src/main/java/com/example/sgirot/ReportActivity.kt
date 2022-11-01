package com.example.sgirot

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.time.LocalDate

class ReportActivity : AppCompatActivity() {
    companion object {
        const val DATE = "date"
        private const val POSITION = "POSITION"

        fun newIntent(context: Context, date: String, position: Int): Intent {
            val intent = Intent(context, ReportActivity::class.java)

            intent.putExtra(DATE, date)
            intent.putExtra(POSITION, position)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val date = intent.extras?.getString(DATE) // Why i have ? here.
        val position = intent.extras?.get(POSITION) as Int
        formatActivity(date, position)
    }

    private fun formatActivity(date: String?, position: Int)
    {
        val dateTitle : TextView = findViewById(R.id.dateTitle)
        val weekendsLeft : TextView = findViewById(R.id.weeksleftText)
        var homeUsers = ""
        var baseUsers = ""

        val data = DB.getInstance(this).data
        dateTitle.text = date
        weekendsLeft.text = if (0 != position) REPORT_WEEKSLEFT_FORMAT.format(position) else REPORT_THIS_WEEK

        data.Info.forEach { entry ->
            val user = entry.key
            val format = REPORT_USERS.format(user)
            if (entry.value[date] == true) {
                homeUsers += format
            }
            else baseUsers += format
        }

        val home: TextView = findViewById(R.id.homeUsers)
        home.text = homeUsers
        val base: TextView = findViewById(R.id.baseUsers)
        base.text = baseUsers
    }
}