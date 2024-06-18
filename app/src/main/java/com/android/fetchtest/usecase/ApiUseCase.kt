package com.android.fetchtest.usecase

import com.android.fetchtest.api.ApiCalling
import com.android.fetchtest.data.Item

class ApiUseCase(private val apiCalling: ApiCalling) : ApiCalling {

    override suspend fun getItems(): List<Item> {
        return apiCalling.getItems()
    }

}