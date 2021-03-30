package com.noemi.android.serviceprovider.landing

import android.content.Context
import androidx.lifecycle.ViewModel
import com.noemi.android.serviceprovider.R
import com.noemi.android.serviceprovider.alarmManager.AlarmManagerActivity
import com.noemi.android.serviceprovider.downLoadManager.DownLoadActivity
import com.noemi.android.serviceprovider.foregroundService.ForegroundServiceActivity
import com.noemi.android.serviceprovider.workManager.select.SelectImageActivity
import com.noemi.android.serviceprovider.util.launchActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {

    fun getListOManagers(): MutableList<ManagerItem> {
        val managers = mutableListOf<ManagerItem>()
        managers.clear()
        managers.add(ManagerItem(R.drawable.ic_upload, WORK_MANAGER))
        managers.add(ManagerItem(R.drawable.ic_download, DOWNLOAD_MANAGER))
        managers.add(ManagerItem(R.drawable.ic_alarms, ALARM_MANAGER))
        managers.add(ManagerItem(R.drawable.ic_foreground, FOREGROUND_SERVICE))
        return managers
    }

    fun openActivity(item: ManagerItem, parent: LandingServiceActivity) {
        when (item.name) {
            WORK_MANAGER -> {
                parent.launchActivity(SelectImageActivity::class.java)
            }

            DOWNLOAD_MANAGER -> {
                parent.launchActivity(DownLoadActivity::class.java)
            }

            ALARM_MANAGER -> {
                parent.launchActivity(AlarmManagerActivity::class.java)
            }

            FOREGROUND_SERVICE -> {
                parent.launchActivity(ForegroundServiceActivity::class.java)
            }

            else -> {
            }
        }
    }

    companion object {

        private const val WORK_MANAGER = "Work Manager"
        private const val DOWNLOAD_MANAGER = "Download Manager"
        private const val ALARM_MANAGER = "Alarm Manager"
        private const val FOREGROUND_SERVICE = "Foreground Service"
    }
}