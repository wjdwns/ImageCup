package com.example.imagecup.data.dataSource

import com.example.imagecup.model.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface RemoteDataSource {
    suspend fun objectDetect(
        imageList : List<MultipartBody.Part>
    ): Flow<List<ObjectDetectResponse>>

    suspend fun uploadFile(
        file: MultipartBody.Part,
        uid: String,
        label: String
    ) : Flow<Unit>

    suspend fun getPhotos(
        getPhotos:GetPhotosRequest
    ): Flow<GetPhotosResponse>

    suspend fun evaluationPhoto(
        evaluationPhoto: EvaluationPhotoRequest
    ) : Flow<Unit>

    suspend fun getRankingPhotos(
        label : String
    ) : Flow<GetRankingPhotosResponse>

}