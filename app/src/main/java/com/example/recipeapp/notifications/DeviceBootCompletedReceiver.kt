package com.example.recipeapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.koin.java.KoinJavaComponent.inject

class DeviceBootCompletedReceiver : BroadcastReceiver() {
    val alarmManagerHelper by inject(AlarmManagerHelper::class.java)
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                alarmManagerHelper.scheduleAlarm()
            }
        }
    }
}