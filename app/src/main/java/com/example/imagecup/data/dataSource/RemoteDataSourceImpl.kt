package com.example.imagecup.data.dataSource

import com.example.imagecup.data.ApiService
import com.example.imagecup.model.*
import com.example.imagecup.utils.PrefsManager.uid
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
    ): Flow<List<ObjectDetectResponse>> = flow {
        emit(apiService.objectDetect(imageList))
    }.flowOn(ioDispatcher)

    override suspend fun uploadFile(
        file: MultipartBody.Part,
        uid: String,
        label: String
    ): Flow<Unit> = flow {
        emit(apiService.uploadFile(file, uid, label))
    }.flowOn(ioDispatcher)

    override suspend fun getPhotos(
        getPhotos:GetPhotosRequest
    ): Flow<GetPhotosResponse> = flow {
        emit(apiService.getPhotos(getPhotos))
    }

    override suspend fun evaluationPhoto(
        evaluationPhoto: EvaluationPhotoRequest
    ): Flow<Unit> = flow {
        emit(apiService.evaluationPhoto(evaluationPhoto))
    }.flowOn(ioDispatcher)

    override suspend fun getRankingPhotos(
        label: String
    ): Flow<GetRankingPhotosResponse> = flow {
        emit(apiService.getRankingPhotos(label))
    }.flowOn(ioDispatcher)


}