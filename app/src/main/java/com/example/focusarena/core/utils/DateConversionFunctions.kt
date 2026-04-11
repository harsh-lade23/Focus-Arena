package com.example.focusarena.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import kotlin.math.abs


object DateConversionFunctions {

    @RequiresApi(Build.VERSION_CODES.O)
    fun differenceMinutesInTwoEpochSeconds(date1: Long, date2: Long): Int{

        val diff = abs(date1-date2)
        val minuteDiff = Instant.ofEpochSecond(diff)
            .atZone(ZoneId.systemDefault())
            .minute


        return minuteDiff


    }

    fun findRemainingTime(startedAt: Long, durationDays: Int): String{
        val spendMinutes = differenceMinutesInTwoEpochSeconds(startedAt, System.currentTimeMillis())

        val totalMinutes = durationDays*24*60

        val remainingMinutes = totalMinutes - spendMinutes


        val hours = remainingMinutes/60
        val minutes = remainingMinutes % 60

        return "$hours days $minutes min"



    }
}