package com.noemi.android.serviceprovider.workManager.dataSource

import com.noemi.android.serviceprovider.datasource.imgur.ImgurResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImgurAPI {

    @Multipart
    @POST("upload")
    fun postImage(@Part image: MultipartBody.Part): Call<ImgurResponse>
}