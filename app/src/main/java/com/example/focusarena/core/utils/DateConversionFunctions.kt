package com.example.focusarena.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.math.abs


object DateConversionFunctions {

    @RequiresApi(Build.VERSION_CODES.O)
    fun differenceDaysInTwoLong(date1: Long, date2: Long): String{

        val diff = abs(date1-date2)
        val minuteDiff = Instant.ofEpochSecond(diff)
            .atZone(ZoneId.systemDefault())
            .minute

        val hours = minuteDiff/60
        val minutes = minuteDiff % 60

        return "$hours days $minutes min"


    }
}