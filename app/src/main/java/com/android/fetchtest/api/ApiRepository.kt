package com.android.fetchtest.api

import com.android.fetchtest.data.Item

interface ApiRepository {
    suspend fun getItems(): List<Item>
}