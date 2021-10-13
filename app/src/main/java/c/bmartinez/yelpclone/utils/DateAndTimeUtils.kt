package c.bmartinez.yelpclone.utils

import android.annotation.SuppressLint
import c.bmartinez.yelpclone.data.model.BusinessHours
import java.time.format.DateTimeFormatter
import java.util.*

fun getCurrentCalendarDay(): Int{
    val day = Calendar
        .getInstance()
        .get(Calendar.DAY_OF_WEEK)
    var apiDay = 0

    when(day) {
        1 -> apiDay = 6
        2 -> apiDay = 0
        3 -> apiDay = 1
        4 -> apiDay = 2
        5 -> apiDay = 3
        6 -> apiDay = 4
        7 -> apiDay = 5
    }
    return apiDay
}

fun getCurrentTime(): String {
    val timeZone = TimeZone.getDefault()
    val currentTime = Date()
    val offsetFromUtc = timeZone.getOffset(currentTime.time) / 3600000.0
    return offsetFromUtc.toString()
}

@SuppressLint("NewApi")
fun checkCurrentTimeInRange(timeSlot: BusinessHours): Boolean {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)

    val currentTime = getCurrentTime()
    val startTime = Date(currentTime)
    val endTime = Date(timeSlot.end)
    val difference = endTime.time - startTime.time
    return difference > 0
}

//fun getRemainingTimeUponClosing()