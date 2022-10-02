package com.example.recipeapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.recipeapp.main.MainViewModel
import com.example.recipeapp.R
import com.example.recipeapp.notifications.AlarmManagerHelper
import com.example.recipeapp.notifications.NotificationHelper
import org.koin.android.ext.android.inject

class DrinkReminderService : Service() {

    private val mainViewModel by inject<MainViewModel>()
    private val notificationHelper by inject<NotificationHelper>()
    private val alarmManagerHelper by inject<AlarmManagerHelper>()


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("RECIPEAPP", "OnCreate")
        startForeground(
            NotificationHelper.FOREGROUND_NOTIFICATION_ID,
            notificationHelper.createForegroundNotification(this)
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("RECIPEAPP", "OnDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("RECIPEAPP", "onStartCommand")
        Log.d("RECIPEAPP", "Rescheduling the alarm")
        alarmManagerHelper.scheduleAlarm()
        val _recipes = mainViewModel.getFavouriteRecipe()
        Log.d("RECIPEAPP", "$_recipes")
        if (_recipes.isEmpty()) {
            notificationHelper.showNotification(
                getString(R.string.feeling_thirsty),
                getString(R.string.need_some_drink),
                ""
            )
        } else {
            val _recipe = _recipes[0]
            notificationHelper.showNotification(
                _recipe.name.toString(),
                _recipe.details.toString(),
                _recipe.thumbnail.toString()
            )
        }

        stopSelf()
        return START_STICKY
    }
}