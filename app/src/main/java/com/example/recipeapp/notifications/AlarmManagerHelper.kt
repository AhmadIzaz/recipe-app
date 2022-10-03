package com.example.recipeapp.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.util.Log
import java.util.*


class AlarmManagerHelper(val context: Context) {

    private var hasPermission: Boolean
    private var alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    companion object {

        private const val ALARM_REQUEST_CODE = 1234
    }

    init {
        hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    // Because we need the alarm at 2 PM
    fun scheduleAlarm(hour: Int = 14, minutes: Int = 0, seconds: Int = 0) {
        if (!hasPermission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                context.startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
            }
        } else {
            val cal = Calendar.getInstance();
            if (cal.get(Calendar.HOUR_OF_DAY) >= hour && ((cal.get(Calendar.MINUTE) >= minutes))) {
                Log.d("RECIPEAPP", "Scheduling the alarm for next day")
                // Scheduling the alarm for next day
                cal.add(Calendar.DAY_OF_YEAR, 1)
            }
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minutes)
            cal.set(Calendar.SECOND, seconds)
            cal.set(Calendar.MILLISECOND, 0)

            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(cal.timeInMillis, createExactAlarmPendingIntent()),
                createExactAlarmPendingIntent()
            )

            Log.d("RECIPEAPP", "Scheduling the alarm")
        }
    }

    private fun createExactAlarmPendingIntent(): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            createExactAlarmIntent(),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createExactAlarmIntent(): Intent {
        return Intent(context, DrinkReminderBroadCastReceiver::class.java)
    }

    fun isAlarmAlreadySet(): Boolean {
        return (PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            createExactAlarmIntent(),
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        ) != null)
    }
}