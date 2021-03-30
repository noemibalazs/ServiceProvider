package com.noemi.android.serviceprovider.workManager.upload

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import com.bumptech.glide.Glide
import com.noemi.android.serviceprovider.R
import com.noemi.android.serviceprovider.databinding.ActivityUploadImageBinding
import com.noemi.android.serviceprovider.util.PHOTO_URI_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadImageBinding
    private val uploadViewModel: UploadViewModel by viewModels()

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_image)

        val photoUriString = intent?.getStringExtra(PHOTO_URI_KEY)
        Log.d(TAG, "The passed uri is: $photoUriString")

        photoUriString?.let {
            Glide.with(this).load(it).into(binding.ivToUpload)
            uploadViewModel.setInputUri(it)
        }

        binding.tvCancel.setOnClickListener {
            uploadViewModel.cancelWork()
        }

        binding.tvUpload.setOnClickListener {
            uploadViewModel.uploadImage()
        }

        binding.tvSeeUploadedPhoto.setOnClickListener {
            uploadViewModel.outPutUri?.let {
                val actionView = Intent(Intent.ACTION_VIEW, it)

                if (actionView.resolveActivity(packageManager) != null)
                    startActivity(actionView)
            }
        }

        uploadViewModel.outPutWorkInfo.observe(this, Observer {
            observeWorkInfo(it)
        })
    }

    private fun observeWorkInfo(listWorkInfo: List<WorkInfo>) {

        if (listWorkInfo.isNullOrEmpty())
            return

        val workInfo = listWorkInfo[0]

        if (workInfo.state.isFinished) {
            showWorkFinished()

            val outPutUri = workInfo.outputData.getString(PHOTO_URI_KEY)

            if (!outPutUri.isNullOrEmpty()) {
                uploadViewModel.setOutPutUri(outPutUri)
                binding.tvSeeUploadedPhoto.visibility = VISIBLE
            }

        } else {
            showWorkInProgress()
        }

    }

    private fun showWorkInProgress() {
        with(binding) {
            progressBar.visibility = VISIBLE
            tvSeeUploadedPhoto.visibility = GONE
        }
    }

    private fun showWorkFinished() {
        with(binding) {
            progressBar.visibility = GONE
        }
    }

    companion object {
        private val TAG = UploadImageActivity::class.java.simpleName
    }
}