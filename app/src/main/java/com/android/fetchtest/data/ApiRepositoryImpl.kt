package com.android.fetchtest.data

import com.android.fetchtest.domain.ApiRepository
import com.android.fetchtest.domain.Item

class ApiRepositoryImpl(private val apiService: ApiService) : ApiRepository {

    override suspend fun getItems(): List<Item> {
        return apiService.getItems()
    }

}