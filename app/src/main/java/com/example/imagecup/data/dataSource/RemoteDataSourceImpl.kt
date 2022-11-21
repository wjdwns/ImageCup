package com.example.imagecup.data.dataSource

import com.example.imagecup.data.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): RemoteDataSource {
}