package com.example.imagecup.data.dataSource

import com.example.imagecup.model.ObjectDetectRequest
import com.example.imagecup.model.ObjectDetectResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface RemoteDataSource {
    suspend fun objectDetect(
        imageList : List<MultipartBody.Part>
    ): Flow<List<ObjectDetectResponse>>
}