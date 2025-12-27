package com.android.fetchtest.data

import com.android.fetchtest.data.model.ItemDTO
import retrofit2.http.GET

interface ApiService {
    @GET(value = "hiring.json")
    suspend fun getItems(): List<ItemDTO>
}
