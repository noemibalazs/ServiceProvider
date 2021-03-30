package com.noemi.android.serviceprovider.datasource.imgur

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImgurResponse(
    @Json(name = "data") val data: ImgurData,
    @Json(name = "success") val success: Boolean,
    @Json(name = "status") val status: Int
) {
    override fun toString(): String {
        return "ImgurResponse: data=$data, success=$success, status=$status"
    }
}

@JsonClass(generateAdapter = true)
data class ImgurData(
    @Json(name = "id") val id: String,
    @Json(name = "link") val link: String
) {
    override fun toString(): String {
        return "ImgurData: id='$id', link='$link'"
    }
}