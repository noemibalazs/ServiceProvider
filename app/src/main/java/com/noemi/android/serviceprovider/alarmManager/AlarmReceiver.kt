package com.noemi.android.serviceprovider.alarmManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.noemi.android.serviceprovider.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            notifyUserTimeToOrder(context)
        }
    }

    private fun notifyUserTimeToOrder(context: Context?) {
        context?.let {
            val intent = launchOrderActivity(it)
            val notification = NotificationCompat.Builder(it, CHANNEL_ID)
                .setContentTitle(it.getString(R.string.txt_notification_title))
                .setContentText(it.getString(R.string.txt_notification_content))
                .setSmallIcon(R.drawable.icon)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(it.getString(R.string.txt_notification_content))
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(intent)
                .setAutoCancel(true)
                .build()
            val manager = NotificationManagerCompat.from(it)
            manager.notify(12, notification)

            checkNotificationChannelIsAvailable(it)
        }
    }

    private fun launchOrderActivity(context: Context): PendingIntent {
        val intent = Intent(context, OrderLunchActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(context, 21, intent, 0)
    }

    private fun checkNotificationChannelIsAvailable(context: Context) {
        if (Build.VERSION.SDK_INT >= 26) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_TITLE, NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {

        private const val CHANNEL_ID = "12"
        private const val CHANNEL_TITLE = "Service Provider"
    }
}