package com.noemi.android.serviceprovider.foregroundService

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.noemi.android.serviceprovider.R
import com.noemi.android.serviceprovider.databinding.ActivityForegroundServiceBinding
import com.noemi.android.serviceprovider.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_foreground_service.*

@AndroidEntryPoint
class ForegroundServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForegroundServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_foreground_service)

        tvPlayMusic.setOnClickListener {
            startMusicService()
        }
    }

    private fun startMusicService() {
        val intent = Intent(this, ForegroundService::class.java)
        intent.action = SHOW_NOTIFICATION
        ContextCompat.startForegroundService(this, intent)
    }

    override fun onResume() {
        super.onResume()
        getIntentActions()
    }

    private fun getIntentActions() {
        intent?.let {
            handleAction(it.action)
        }
    }

    private fun handleAction(action: String?) {
        action?.let {
            if (it == NOTIFICATION_PLAY_MUSIC) {
                val player = MediaPlayer.create(this, R.raw.mj_who_is_it)
                player.start()
            }

            if (it == NOTIFICATION_PAUSE_MUSIC) {
                val player = MediaPlayer.create(this, R.raw.mj_who_is_it)
                player.stop()
            }
        }
    }

    companion object {

        private val TAG = ForegroundServiceActivity::class.java.simpleName
    }
}