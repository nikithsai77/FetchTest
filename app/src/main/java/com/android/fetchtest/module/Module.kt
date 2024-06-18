package com.android.fetchtest.module

import com.android.fetchtest.api.ApiCalling
import com.android.fetchtest.api.ApiService
import com.android.fetchtest.data.ApiCallingImpl
import com.android.fetchtest.usecase.ApiUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideApiCalling(apiService: ApiService) : ApiCalling {
        return ApiCallingImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideApiUseCase(apiCalling: ApiCalling) : ApiUseCase {
        return ApiUseCase(apiCalling)
    }
}