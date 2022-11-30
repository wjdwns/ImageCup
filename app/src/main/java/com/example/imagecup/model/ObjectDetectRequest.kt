package com.example.imagecup.model

import okhttp3.MultipartBody

data class ObjectDetectRequest(
    val imageList : List<MultipartBody.Part>
)
