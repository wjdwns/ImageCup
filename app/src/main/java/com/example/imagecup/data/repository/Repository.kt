package com.example.imagecup.data.repository

import com.example.imagecup.data.dataSource.RemoteDataSourceImpl
import com.example.imagecup.data.room.PhotoDao
import com.example.imagecup.model.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSourceImpl,
    private val photoDao: PhotoDao
) {
    suspend fun objectDetect(
        url : String,
        imageList: List<MultipartBody.Part>
    ): Flow<List<ObjectDetectResponse>> = remoteDataSource.objectDetect(url,imageList)

    suspend fun insertPhoto(photoList: List<Photo>) = photoDao.insertPhoto(photoList)

    suspend fun getAllLabels(): List<Photo> = photoDao.getAllLabels()

    suspend fun getAllPhotos(label: String): List<Photo> = photoDao.getPhotos(label)

    suspend fun uploadFile(
        file: MultipartBody.Part,
        uid: RequestBody,
        label: RequestBody
    ): Flow<Unit> =
        remoteDataSource.uploadFile(file, uid, label)

    suspend fun getPhotos(label: String, uid: String, page: Int): Flow<GetPhotosResponse> =
        remoteDataSource.getPhotos(label, uid, page)

    suspend fun evaluationPhoto(pid: Int, uid: String, scoreValue: Float): Flow<Unit> =
        remoteDataSource.evaluationPhoto(EvaluationPhotoRequest(pid, uid, scoreValue))

    suspend fun getRankingPhotos(label: String): Flow<List<GetRankingPhotosResponse>> =
        remoteDataSource.getRankingPhotos(label)
}