package com.noemi.android.serviceprovider.downLoadManager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.noemi.android.serviceprovider.R
import com.noemi.android.serviceprovider.databinding.ActivityDownLoadBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownLoadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDownLoadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_down_load)

        binding.tvDownLoadPhoto.setOnClickListener {

            val intent = Intent().apply {
                putExtra(DownloadPhotoManager.KEY_URL, PHOTO_URL_1)
                putExtra(DownloadPhotoManager.KEY_PATH, PATH_DIRECTORY)
            }
            DownloadPhotoManager().enqueueWork(this, intent)
        }
    }

    companion object {

        const val PHOTO_URL_1 = "https://upload.wikimedia.org/wikipedia/en/f/f9/Basic_Instinct.png"
        const val PHOTO_URL_2 =
            "https://i.pinimg.com/originals/c5/0e/18/c50e18a6297ff062ca9d2d0cd5fb603c.jpg"

        const val PATH_DIRECTORY = "DownLoadManager/"
    }
}