package com.noemi.android.serviceprovider.foregroundService

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.noemi.android.serviceprovider.R
import com.noemi.android.serviceprovider.util.*

@Suppress("DEPRECATION")
class ForegroundService : Service() {

    private lateinit var player: MediaPlayer
    private var position = 0

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.mj_who_is_it)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {

            when (it.action) {
                SHOW_NOTIFICATION -> onCreateForeGroundNotification()

                STOP_FOREGROUND_SERVICE -> {
                    stopSelf()
                    stopForeground(true)
                }

                NOTIFICATION_PLAY_MUSIC -> {
                    if (!player.isPlaying) {
                        player.start()
                    } else {
                        player.seekTo(position)
                        player.start()
                    }
                }
                NOTIFICATION_PAUSE_MUSIC -> {
                    player.pause()
                    position = player.currentPosition
                }
                else -> {
                    player.release()
                    player.stop()
                }
            }
        }
        return START_NOT_STICKY
    }

    private fun onCreateForeGroundNotification() {
        val remoteView = RemoteViews(packageName, R.layout.play_music_layout)

        val play = getPlayIntent()
        remoteView.setImageViewResource(R.id.ivPlay, R.drawable.ic_play)
        remoteView.setOnClickPendingIntent(R.id.ivPlay, play)

        val pause = getPauseIntent()
        remoteView.setImageViewResource(R.id.ivPause, R.drawable.ic_pause)
        remoteView.setOnClickPendingIntent(R.id.ivPause, pause)

        val stop = getStopIntent()
        remoteView.setImageViewResource(R.id.ivStop, R.drawable.ic_stop)
        remoteView.setOnClickPendingIntent(R.id.ivStop, stop)

        val notification = NotificationCompat.Builder(this, FOREGROUND_ID).apply {
            priority = NotificationCompat.PRIORITY_HIGH
            setSmallIcon(R.drawable.icon)
        }.build()

        notification.bigContentView = remoteView
        notification.flags = NotificationCompat.FLAG_ONGOING_EVENT

        checkChannelAvailable()

        startForeground(21, notification)
    }

    private fun getPlayIntent(): PendingIntent {
        val play = Intent(this, ForegroundService::class.java)
        play.action = NOTIFICATION_PLAY_MUSIC
        return PendingIntent.getService(this, 3, play, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getPauseIntent(): PendingIntent {
        val pause = Intent(this, ForegroundService::class.java)
        pause.action = NOTIFICATION_PAUSE_MUSIC
        return PendingIntent.getService(this, 6, pause, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getStopIntent(): PendingIntent {
        val stop = Intent(this, ForegroundService::class.java)
        stop.action = STOP_FOREGROUND_SERVICE
        return PendingIntent.getService(this, 9, stop, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun checkChannelAvailable() {
        if (Build.VERSION.SDK_INT >= 26) {
            val manager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val chanel = NotificationChannel(
                FOREGROUND_ID,
                FOREGROUND_TITLE,
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(chanel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {

        private const val FOREGROUND_ID = "foreground_id"
        private const val FOREGROUND_TITLE = "foreground_title"
    }
}