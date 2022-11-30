package com.example.imagecup.data

import com.example.imagecup.model.ObjectDetectRequest
import com.example.imagecup.model.ObjectDetectResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/ObjectDetect")
    suspend fun objectDetect(
        @Part file : List<MultipartBody.Part>
    ): List<ObjectDetectResponse>
}