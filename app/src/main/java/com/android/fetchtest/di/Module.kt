package com.android.fetchtest.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import dagger.hilt.InstallIn
import javax.inject.Singleton
import okhttp3.OkHttpClient
import com.android.fetchtest.data.*
import com.android.fetchtest.domain.*
import com.android.fetchtest.BuildConfig
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService) : Repository {
        return ApiRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideApiUseCase(repository: Repository) : ApiUseCase {
        return ApiUseCase(repository)
    }
}
