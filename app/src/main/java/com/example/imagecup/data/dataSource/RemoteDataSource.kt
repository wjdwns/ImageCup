package com.example.imagecup.data.dataSource

import com.example.imagecup.model.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface RemoteDataSource {
    suspend fun objectDetect(
        url : String,
        imageList : List<MultipartBody.Part>
    ): Flow<List<ObjectDetectResponse>>

    suspend fun uploadFile(
        file: MultipartBody.Part,
        uid: RequestBody,
        label: RequestBody
    ) : Flow<Unit>

    suspend fun getPhotos(
        label: String,
        uid : String,
        page : Int
    ): Flow<GetPhotosResponse>

    suspend fun evaluationPhoto(
        evaluationPhoto: EvaluationPhotoRequest
    ) : Flow<Message>

    suspend fun getRankingPhotos(
        label : String
    ) : Flow<List<GetRankingPhotosResponse>>

}