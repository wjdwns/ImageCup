package com.example.imagecup.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EvaluationPhotoRequest(
    val pid: String,
    val uid: String,
    val scoreValue: Int
):Parcelable