package com.noemi.android.serviceprovider.downLoadManager

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.JobIntentService

class DownloadPhotoManager : JobIntentService() {

    fun enqueueWork(context: Context, work: Intent) {
        enqueueWork(context, DownloadPhotoManager::class.java, 12, work)
    }

    override fun onHandleWork(intent: Intent) {
        val photoUrl = intent.getStringExtra(KEY_URL)
        val destinationPath = intent.getStringExtra(KEY_PATH)
        photoUrl?.let { url ->
            destinationPath?.let { path ->
                downLoadPhoto(url, path)
            }
        }
    }

    private fun downLoadPhoto(photoUrl: String, destinationPath: String) {
        val uri = Uri.parse(photoUrl)
        val downloadManager = DownloadManager.Request(uri)
        downloadManager.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        downloadManager.setTitle(DOWNLOAD_MANAGER_TITLE)
        downloadManager.setDestinationInExternalPublicDir(destinationPath, uri.lastPathSegment)
        (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(downloadManager)
    }

    companion object {
        const val DOWNLOAD_MANAGER_TITLE = "Downloading a photo"
        const val KEY_URL = "key_url"
        const val KEY_PATH = "key_path"
    }
}