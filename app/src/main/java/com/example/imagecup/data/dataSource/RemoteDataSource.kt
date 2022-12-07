package com.example.imagecup.data.dataSource

import com.example.imagecup.model.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface RemoteDataSource {
    suspend fun objectDetect(
        imageList : List<MultipartBody.Part>
    ): Flow<List<ObjectDetectResponse>>

    suspend fun uploadFile(
        file: MultipartBody.Part,
        uid: RequestBody,
        label: RequestBody
    ) : Flow<Message>

    suspend fun getPhotos(
        getPhotos:GetPhotosRequest
    ): Flow<GetPhotosResponse>

    suspend fun evaluationPhoto(
        evaluationPhoto: EvaluationPhotoRequest
    ) : Flow<Message>

    suspend fun getRankingPhotos(
        label : String
    ) : Flow<GetRankingPhotosResponse>

}