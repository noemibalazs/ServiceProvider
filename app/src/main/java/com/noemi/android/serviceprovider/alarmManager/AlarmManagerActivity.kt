package com.noemi.android.serviceprovider.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.noemi.android.serviceprovider.R
import com.noemi.android.serviceprovider.databinding.ActivityAlarmManagerBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

@AndroidEntryPoint
class AlarmManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmManagerBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm_manager)

        binding.tvSetAlarm.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                binding.tpAlarm.hour,
                binding.tpAlarm.minute,
                0
            )

            setUpDailyAlarm(calendar.timeInMillis)
        }
    }

    private fun setUpDailyAlarm(time: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 12, intent, 0)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            time,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        val hourToShow =
            String.format(getString(R.string.txt_your_daily_alarm_toast, getHourToShowUser(time)))
        Toast.makeText(this, hourToShow, Toast.LENGTH_LONG).show()
    }

    private fun getHourToShowUser(time: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(time))
    }
}