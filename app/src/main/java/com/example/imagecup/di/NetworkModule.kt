package com.example.imagecup.di

import com.example.imagecup.data.ApiService
import com.example.imagecup.data.dataSource.RemoteDataSourceImpl
import com.example.imagecup.utils.PrefsManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.annotation.Documented
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.xml.transform.OutputKeys

const val NETWORK_ERROR = 1001

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule{
    private val BASE_URL = "http://54.180.55.20:8000"

    private val networkInterceptor: Interceptor = Interceptor { chain ->
        val request = chain.request()
        try {
            chain.proceed(
                request.newBuilder()
                    .build()

            )
        } catch (e: Exception) {
            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(NETWORK_ERROR)
                .message(e.message ?: "")
                .body((e.message ?: "").toResponseBody(null))
                .build()
        }
    }
    @Singleton
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .addInterceptor(networkInterceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClientBuilder: OkHttpClient.Builder): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSourceImpl {
        return RemoteDataSourceImpl(apiService, Dispatchers.IO)
    }
}