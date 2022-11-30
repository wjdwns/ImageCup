package com.example.imagecup.data.dataSource

import com.example.imagecup.data.ApiService
import com.example.imagecup.model.ObjectDetectResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) : RemoteDataSource {
    override suspend fun objectDetect(
        imageList: List<MultipartBody.Part>
    ): Flow<List<ObjectDetectResponse>> = flow{
        emit(apiService.objectDetect(imageList))
    }.flowOn(ioDispatcher)


}