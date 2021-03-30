package com.noemi.android.serviceprovider.workManager.worker

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.noemi.android.serviceprovider.workManager.remoteDataSource.ImgurRemoteData
import com.noemi.android.serviceprovider.util.PHOTO_URI_KEY
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ImgurUploadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val remoteData: ImgurRemoteData
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        var imageUriInput: String? = null
        return try {

            val args = inputData
            imageUriInput = args.getString(PHOTO_URI_KEY)
            val imageUri = Uri.parse(imageUriInput)
            Log.d(TAG, "See the image uri: $imageUri")

            val resp = remoteData.uploadImage(imageUri)
            val response = resp.execute()

            if (!response.isSuccessful) {
                val errorBody = response.errorBody()
                val error = errorBody?.string()
                Log.e(TAG, "Request failed to upload image, see error: $error")
                Result.failure()
            } else {
                val imageResponse = response.body()
                var outputData = workDataOf()
                if (imageResponse != null) {
                    val imageUrl = imageResponse.data.link
                    outputData = Data.Builder()
                        .putString(PHOTO_URI_KEY, imageUrl)
                        .build()
                }
                Result.success(outputData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception thrown, failed to upload image with URI $imageUriInput")
            Result.failure()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun workDataOf(vararg pairs: Pair<String, Any?>): Data {
        val dataBuilder = Data.Builder()
        for (pair in pairs) {
            dataBuilder.put(pair.first, pair.second)
        }
        return dataBuilder.build()
    }

    companion object {
        private val TAG = ImgurUploadWorker::class.java.simpleName
    }
}