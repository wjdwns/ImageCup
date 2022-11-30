package com.example.imagecup.data.repository

import com.example.imagecup.data.dataSource.RemoteDataSource
import com.example.imagecup.data.room.PhotoDao
import com.example.imagecup.model.ObjectDetectRequest
import com.example.imagecup.model.ObjectDetectResponse
import com.example.imagecup.model.Photo
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val photoDao: PhotoDao
){
    suspend fun objectDetect(
        imageList : List<MultipartBody.Part>
    ): Flow<List<ObjectDetectResponse>> = remoteDataSource.objectDetect(imageList)

    suspend fun insertPhoto(photoList : List<Photo>) = photoDao.insertPhoto(photoList)

    suspend fun getAllLabels():List<Photo> = photoDao.getAllLabels()

    suspend fun getAllPhotos(label : String):List<Photo> = photoDao.getPhotos(label)
}