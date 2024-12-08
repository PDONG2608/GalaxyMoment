package com.example.galaxymoment.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateUtils {
    companion object {

        /**
         * Converts a given time in milliseconds to a formatted date string.
         *
         * @param timeInMillis The time in milliseconds.
         * @return A string representing the date in the format "EEEE, dd/MM/yyyy".
         */
        fun convertLongToDate(timeInMillis: Long): String {
            val dateFormat = SimpleDateFormat("EEEE, dd/MM/yyyy", Locale.ENGLISH)
            val date = Date(timeInMillis)
            return dateFormat.format(date)
        }
    }
}