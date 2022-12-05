package com.example.imagecup.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ObjectDetectResponse(
    val label : String
):Parcelable
