package com.example.imagecup.di

import com.example.imagecup.data.dataSource.RemoteDataSourceImpl
import com.example.imagecup.data.repository.Repository
import com.example.imagecup.data.room.PhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideMainRepository(remoteDataSourceImpl: RemoteDataSourceImpl, databaseDao: PhotoDao): Repository {
        return Repository(remoteDataSourceImpl, databaseDao)
    }
}