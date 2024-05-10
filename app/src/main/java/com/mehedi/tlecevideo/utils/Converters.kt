package com.mehedi.tlecevideo.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object Converters {

    fun fromTimestamp(value: Long?): String? {
        if (value == null) return null

        val date = Date(value)

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val time = timeFormat.format(date)
        val formattedDate = dateFormat.format(date)

        return "$time, $formattedDate"
    }


    fun timeAndDateToTimestamp(formattedString: String?): Long? {
        return formattedString?.let {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val date = dateFormat.parse(formattedString)
            date?.time
        }
    }
}