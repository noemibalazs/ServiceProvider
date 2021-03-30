package com.noemi.android.serviceprovider.alarmManager

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import com.noemi.android.serviceprovider.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderLunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_lunch)

        callForYourLunch()
    }

    private fun callForYourLunch() {
        if (checkTelephonyManager()) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+441926839581") // Just Eat
            startActivity(intent)
        }
    }

    private fun checkTelephonyManager(): Boolean {
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.simState == TelephonyManager.SIM_STATE_READY
    }
}