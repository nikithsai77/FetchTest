package com.android.fetchtest.data

import com.android.fetchtest.api.ApiCalling
import com.android.fetchtest.api.ApiService

class ApiCallingImpl(private val apiService: ApiService) : ApiCalling {

    override suspend fun getItems(): List<Item> {
        return apiService.getItems()
    }

}