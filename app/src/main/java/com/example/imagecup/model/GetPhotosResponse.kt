package com.example.imagecup.model

data class GetPhotosResponse(
    val photoId: Int,
    val userId: String,
    val path: String,
    val avgScore: Double
)
