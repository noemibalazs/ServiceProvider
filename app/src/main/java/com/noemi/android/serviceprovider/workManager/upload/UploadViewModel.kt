package com.noemi.android.serviceprovider.workManager.upload

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.noemi.android.serviceprovider.util.IMAGE_MANIPULATION_WORK_NAME
import com.noemi.android.serviceprovider.util.PHOTO_URI_KEY
import com.noemi.android.serviceprovider.util.TAG_OUTPUT
import com.noemi.android.serviceprovider.workManager.worker.ImgurUploadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    private val workManager = WorkManager.getInstance(context.applicationContext)

    internal var outPutUri: Uri? = null
    private var inputUri: Uri? = null

    internal val outPutWorkInfo: LiveData<List<WorkInfo>>

    init {
        outPutWorkInfo = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    internal fun uploadImage() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val uploadWorker =
            OneTimeWorkRequest.Builder(ImgurUploadWorker::class.java).setConstraints(constraints)
                .setInputData(createInputData())
                .addTag(TAG_OUTPUT)
                .build()

        workManager.beginUniqueWork(
            IMAGE_MANIPULATION_WORK_NAME, ExistingWorkPolicy.KEEP, uploadWorker
        ).enqueue()
    }

    internal fun setInputUri(uri: String?) {
        inputUri = setUriOrNull(uri)
    }

    internal fun setOutPutUri(uriOfImage: String?) {
        outPutUri = setUriOrNull(uriOfImage)
    }

    private fun setUriOrNull(uri: String?): Uri? {
        return if (!uri.isNullOrEmpty()) {
            Uri.parse(uri)
        } else {
            null
        }
    }

    @SuppressLint("RestrictedApi")
    private fun createInputData(): Data {
        val builder = Data.Builder()
        inputUri?.let {
            builder.put(PHOTO_URI_KEY, it.toString())
        }
        return builder.build()
    }
}