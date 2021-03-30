package com.noemi.android.serviceprovider.workManager.remoteDataSource

import android.content.Context
import android.net.Uri
import com.noemi.android.serviceprovider.workManager.dataSource.ImgurAPI
import com.noemi.android.serviceprovider.datasource.imgur.ImgurResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImgurRemoteDataSource @Inject constructor(
    private val imgurAPI: ImgurAPI,
    @ApplicationContext val context: Context
) : ImgurRemoteData {

    override fun uploadImage(imageUri: Uri): Call<ImgurResponse> {
        val file = File(imageUri.path!!)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        return imgurAPI.postImage(body)
    }
}