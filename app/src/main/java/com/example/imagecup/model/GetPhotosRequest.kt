package com.example.imagecup.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetPhotosRequest(
    val label: String,
    val uid: String,
    val page: Int
):Parcelable