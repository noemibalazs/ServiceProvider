package com.noemi.android.serviceprovider.workManager.select

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.noemi.android.serviceprovider.R
import com.noemi.android.serviceprovider.databinding.ActivitySelectImageBinding
import com.noemi.android.serviceprovider.util.*
import com.noemi.android.serviceprovider.workManager.upload.UploadImageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_image)

        binding.tvSelectPhoto.setOnClickListener {
            selectPhoto()
        }
    }

    private fun selectPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(intent, CHOOSE_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_PICTURE && resultCode == Activity.RESULT_OK) {
            handleResponse(data)
        } else {
            Log.e(TAG, String.format("Unexpected error, see result code: %d", resultCode))
        }
    }

    private fun handleResponse(data: Intent?) {
        var imageUri: Uri? = null

        data?.let {
            imageUri = it.clipData?.getItemAt(0)?.uri ?: it.data
        }

        if (imageUri == null) {
            Toast.makeText(this, "Try it again, something went wrong!", Toast.LENGTH_LONG).show()
            return
        } else {
            val filePath = getFilePathFromUri(imageUri!!)
            filePath?.let {
                openUploadActivity(it)
            }
        }
    }

    private fun getFilePathFromUri(imageUri: Uri): String? {
        var filePtah: String? = null
        if (CONTENT_SCHEME == imageUri.scheme) {
            val cursor = this.contentResolver.query(
                imageUri,
                arrayOf(MediaStore.Images.ImageColumns.DATA),
                null, null, null
            )
            cursor?.let {
                it.moveToFirst()
                filePtah = it.getString(0)
            }
            cursor?.close()
        } else {
            filePtah = imageUri.path
        }
        return filePtah
    }

    private fun openUploadActivity(imageFilePath: String) {
        Log.d(TAG, "The image file path is: $imageFilePath")
        val intent = Intent(this, UploadImageActivity::class.java)
        intent.putExtra(PHOTO_URI_KEY, imageFilePath)
        startActivity(intent)
    }

    companion object {

        private val TAG = SelectImageActivity::class.java.simpleName
    }
}