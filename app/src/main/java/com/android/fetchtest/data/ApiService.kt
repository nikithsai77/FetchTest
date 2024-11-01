package com.android.fetchtest.data

import com.android.fetchtest.domain.Item
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}