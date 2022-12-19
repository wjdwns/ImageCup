package com.example.imagecup.data.dataSource

import com.example.imagecup.data.ApiService
import com.example.imagecup.model.*
import com.example.imagecup.utils.PrefsManager.uid
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Query
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) : RemoteDataSource {
    override suspend fun objectDetect(
        url : String,
        imageList: List<MultipartBody.Part>
    ): Flow<List<ObjectDetectResponse>> = flow {
        emit(apiService.objectDetect(url,imageList))
    }.flowOn(ioDispatcher)

    override suspend fun uploadFile(
        file: MultipartBody.Part,
        uid: RequestBody,
        label: RequestBody
    ): Flow<Unit> = flow {
        emit(apiService.uploadFile(file, uid, label))
    }.flowOn(ioDispatcher)

    override suspend fun getPhotos(
        label: String,
        uid : String,
        page : Int
    ): Flow<GetPhotosResponse> = flow {
        emit(apiService.getPhotos(label, uid, page))
    }

    override suspend fun evaluationPhoto(
        evaluationPhoto: EvaluationPhotoRequest
    ): Flow<Unit> = flow {
        emit(apiService.evaluationPhoto(evaluationPhoto))
    }.flowOn(ioDispatcher)

    override suspend fun getRankingPhotos(
        label: String
    ): Flow<List<GetRankingPhotosResponse>> = flow {
        emit(apiService.getRankingPhotos(label))
    }.flowOn(ioDispatcher)


}