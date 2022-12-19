package com.example.imagecup.data

import com.example.imagecup.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST
    suspend fun objectDetect(
        @Url url : String,
        @Part file : List<MultipartBody.Part>
    ): List<ObjectDetectResponse>

    @Multipart
    @POST("/evaluation/upload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
        @Part("uid") uid: RequestBody,
        @Part("label") label: RequestBody
    )

    @GET("evaluation/photos")
    suspend fun getPhotos(
        @Query("label") label: String,
        @Query("userId") userId : String,
        @Query("page")page : Int
    ) : GetPhotosResponse

    @POST("evaluation/score")
    suspend fun evaluationPhoto(
        @Body evaluationPhoto: EvaluationPhotoRequest
    )

    @GET("ranking")
    suspend fun getRankingPhotos(
        @Query("label") label : String
    ) : List<GetRankingPhotosResponse>

}