package com.example.sgirot

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.ToggleButton
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MyAdapter(private val context: Context, private val weekends: List<String>) : BaseAdapter() {
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

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, container: ViewGroup?): View? {
        val weekendListView = inflater.inflate(R.layout.weekend_list_entry, container, false)

        // Weekend Text
        val weekendText : TextView = weekendListView.findViewById(R.id.weekendText)
        weekendText.text = this.weekends[position]

        sgiraButton(weekendListView.findViewById(R.id.sgiraButton), this.weekends[position])
        return weekendListView
    }

    private fun sgiraButton(toggleButton: ToggleButton, date: String)
    {
        val data = DB.getInstance(this.context).data
        toggleButton.setOnClickListener {
            // If Name not here, add it
            if (!data.Info.containsKey(data.Name))
                data.Info[data.Name] = mutableMapOf()
            // Add date and here or not
            data.Info[data.Name]?.set(date, toggleButton.isChecked)
        }

        toggleButton.text = NOT_REPORTED
        if (data.Info[data.Name]?.contains(date) == true)
            toggleButton.isChecked = (data.Info[data.Name]?.get(date) == true)
    }
}