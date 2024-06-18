package com.android.fetchtest.api

import com.android.fetchtest.data.Item

interface ApiCalling {
    suspend fun getItems(): List<Item>
}