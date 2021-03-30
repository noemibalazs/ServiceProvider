package com.noemi.android.serviceprovider.landing

import androidx.annotation.DrawableRes

data class ManagerItem(
    @DrawableRes
    val id: Int,
    val name: String
) {
    override fun toString(): String {
        return "ManagerItem: id=$id, name=$name"
    }
}