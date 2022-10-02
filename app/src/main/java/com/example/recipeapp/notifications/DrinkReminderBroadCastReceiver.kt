package com.example.recipeapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.recipeapp.service.DrinkReminderService

class DrinkReminderBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("RECIPEAPP", "Rings Alarm")
        Log.d("RECIPEAPP", "Starting Service")
        context?.startForegroundService(Intent(context, DrinkReminderService::class.java))
    }
}