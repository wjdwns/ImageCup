package com.example.imagecup.data

import com.example.imagecup.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("/ObjectDetect")
    suspend fun objectDetect(
        @Part file : List<MultipartBody.Part>
    ): List<ObjectDetectResponse>

    @Multipart
    @POST("/evaluation/upload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
        uid: String,
        label: String
    )

    @GET("evaluation/photos")
    suspend fun getPhotos(
        getPhotos : GetPhotosRequest
    ) : GetPhotosResponse

    @POST("evaluation/score")
    suspend fun evaluationPhoto(
        evaluationPhoto: EvaluationPhotoRequest
    )

    @GET("ranking")
    suspend fun getRankingPhotos(
        label : String
    ) : GetRankingPhotosResponse


}