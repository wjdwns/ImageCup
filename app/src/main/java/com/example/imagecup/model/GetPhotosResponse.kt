package com.example.imagecup.model

data class GetPhotosResponse(
    val file : String,
    val score_average : Double,
    val pid : String
)
