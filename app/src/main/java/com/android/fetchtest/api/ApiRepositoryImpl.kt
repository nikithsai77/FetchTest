package com.android.fetchtest.api

import com.android.fetchtest.api.ApiRepository
import com.android.fetchtest.api.ApiService
import com.android.fetchtest.data.Item
import javax.inject.Inject

class ApiRepositoryImpl(private val apiService: ApiService) : ApiRepository {

    override suspend fun getItems(): List<Item> {
        return apiService.getItems()
    }

}