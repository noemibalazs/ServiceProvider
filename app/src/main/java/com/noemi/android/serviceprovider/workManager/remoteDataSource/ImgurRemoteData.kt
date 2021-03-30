package com.noemi.android.serviceprovider.workManager.remoteDataSource

import android.net.Uri
import com.noemi.android.serviceprovider.datasource.imgur.ImgurResponse
import retrofit2.Call

interface ImgurRemoteData {

    fun uploadImage(imageUri: Uri): Call<ImgurResponse>
}