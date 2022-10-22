package com.example.sgirot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.ToggleButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MyAdapter(private val context: Context, private val weekends: List<LocalDate>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return weekends.size
    }
    override fun getItem(position: Int): Any {
        return weekends[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, container: ViewGroup?): View? {
        val weekendListView = inflater.inflate(R.layout.weekend_list_entry, container, false)
        dateText(weekendListView.findViewById(R.id.weekendText), this.weekends[position])
        sgiraButton(weekendListView.findViewById(R.id.sgiraButton), this.weekends[position])
        return weekendListView
    }

    private fun dateText(dateText: TextView, localDate: LocalDate)
    {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM")
        dateText.text = "%s - %s".format(localDate.minusDays(2).format(dateFormatter),
            localDate.format(dateFormatter))
        // TODO style?
    }

    private fun sgiraButton(toggleButton: ToggleButton, localDate: LocalDate)
    {
        toggleButton.setOnClickListener {
            DB.getInstance(this.context).data.Info[localDate.toString()] = toggleButton.isChecked
        }
        toggleButton.isChecked = DEFAULT_VALUE

        val info = DB.getInstance(this.context).data.Info
        if(info.contains(localDate.toString()))
        {
            toggleButton.isChecked = info[localDate.toString()] == true
        }
    }

}