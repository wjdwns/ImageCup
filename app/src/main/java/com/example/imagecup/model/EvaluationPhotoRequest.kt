package com.example.imagecup.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EvaluationPhotoRequest(
    val photoId: Int,
    val userId: String,
    val evaScore: Float
):Parcelable