package com.android.fetchtest.di

import com.android.fetchtest.BuildConfig
import com.android.fetchtest.domain.ApiRepository
import com.android.fetchtest.data.ApiRepositoryImpl
import com.android.fetchtest.data.ApiService
import com.android.fetchtest.domain.ApiUseCase
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
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService) : ApiRepository {
        return ApiRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideApiUseCase(apiRepository: ApiRepository) : ApiUseCase {
        return ApiUseCase(apiRepository)
    }
}