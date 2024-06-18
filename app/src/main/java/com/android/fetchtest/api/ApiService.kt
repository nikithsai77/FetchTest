package com.android.fetchtest.api

import com.android.fetchtest.data.Item
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}