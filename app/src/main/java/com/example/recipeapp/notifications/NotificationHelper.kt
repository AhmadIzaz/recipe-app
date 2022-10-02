package com.example.recipeapp.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.recipeapp.main.MainActivity
import com.example.recipeapp.R


class NotificationHelper(val context: Context) {

    private lateinit var notification: Notification
    private var notificationManager: NotificationManager? =
        context.getSystemService(NotificationManager::class.java)


    companion object {
        private const val CHANNEL_ID = "RECIPE_APP_NOTIFICATION_CHANNEL"
        private const val NOTIFICATION_TAG = "RECIPE_APP_NOTIFICATION_TAG"
        private const val NOTIFICATION_ID = 789456
        const val FOREGROUND_NOTIFICATION_ID = 780156
        private const val FOREGROUND_CHANNEL_ID = "FOREGROUND_RECIPE_APP_NOTIFICATION_CHANNEL"


    }

    fun showNotification(
        title: String,
        text: String,
        imageUrl: String
    ) {
        notification = createNotification(title, text, imageUrl)
        if (imageUrl.isEmpty()) {
            notificationManager?.notify(
                NOTIFICATION_TAG,
                NOTIFICATION_ID,
                this@NotificationHelper.notification
            )
        }
    }

    private fun createNotification(
        title: String,
        text: String,
        imageUrl: String
    ): Notification {
        val notificationBuilder = createDefaultBuilder(title, text)
        try {
            val remoteViews = setCustomNotification(title, text, imageUrl)
            notificationBuilder.setCustomContentView(remoteViews)
            notificationBuilder.setCustomBigContentView(remoteViews)
        } catch (exception: Exception) {
            // Use the default builder as fallback
            notificationBuilder.setBigPictureStyle(title, text, imageUrl)
        }
        return notificationBuilder.build()
    }

    private fun createDefaultBuilder(
        title: String,
        text: String,
        priority: Int = NotificationCompat.PRIORITY_MAX
    ): NotificationCompat.Builder {
        createNotificationChannel(
            "Drinks Reminders Notification",
            "This is channel for drink reminders.",
            CHANNEL_ID
        )
        return NotificationCompat.Builder(context.applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_star) // Required.
            .setPriority(priority)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    NOTIFICATION_ID,
                    Intent(
                        context,
                        MainActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setContentTitle(title) // Required.
            .setContentText(text) // Required.
    }

    private fun createNotificationChannel(name: String, description: String, channelId: String) {
        val drinksReminderNotificationChannel = NotificationChannel(
            channelId,
            name,
            NotificationManager.IMPORTANCE_HIGH
        )
        drinksReminderNotificationChannel.description = description
        notificationManager?.createNotificationChannel(drinksReminderNotificationChannel)
    }

    private fun setCustomNotification(
        title: String,
        text: String,
        imageUrl: String
    ): RemoteViews {
        // inflate the layout and set the values to our UI IDs
        val remoteViews =
            RemoteViews(context.packageName, R.layout.favourite_drink_notif_layout)

        remoteViews.setTextViewText(R.id.name_textview, title)
        remoteViews.setTextViewText(R.id.details_textview, text)
        remoteViews.setTextViewText(R.id.time_textview, "02:00 PM")

        Log.d("RECIPEAPP", "ImageUrl -- $imageUrl")
        if (imageUrl.isEmpty()) {
            Log.d("RECIPEAPP", "In Empty")
            remoteViews.setImageViewResource(
                R.id.drink_thumbnail_imageview,
                R.drawable.ic_launcher_foreground
            )
        } else {
            Log.d("RECIPEAPP", "In Not Empty")
            Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        remoteViews.setImageViewBitmap(R.id.drink_thumbnail_imageview, resource)
                        notificationManager?.notify(
                            NOTIFICATION_TAG,
                            NOTIFICATION_ID,
                            this@NotificationHelper.notification
                        )
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // this is called when imageView is cleared on lifecycle call or for
                        // some other reason.
                        // if you are referencing the bitmap somewhere else too other than this imageView
                        // clear it here as you can no longer have the bitmap
                    }
                })
        }

        return remoteViews
    }

    private fun NotificationCompat.Builder.setBigPictureStyle(
        title: String,
        text: String,
        imageUrl: String
    ): NotificationCompat.Builder {
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    setLargeIcon(resource)
                    setStyle(
                        NotificationCompat.BigPictureStyle()
                            .bigPicture(resource)
                            .setBigContentTitle(title)
                            .setSummaryText(text)
                    )
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })

        return this
    }

    fun createForegroundNotification(context: Context): Notification {
        createNotificationChannel(
            "Foreground Service", "Foreground Service for recipe app",
            FOREGROUND_CHANNEL_ID
        )
        val builder = Notification.Builder(context, FOREGROUND_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText("RecipeApp running")
            .setAutoCancel(true)
        return builder.build()
    }
}