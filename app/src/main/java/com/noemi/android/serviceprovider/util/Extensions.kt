package com.noemi.android.serviceprovider.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.noemi.android.serviceprovider.R
import com.noemi.android.serviceprovider.application.App_HiltComponents

fun Activity.launchActivity(dest: Class<*>) {
    this.startActivity(Intent(this, dest))
    this.overridePendingTransition(R.anim.anim_enter, R.anim.anim_leave)
}
