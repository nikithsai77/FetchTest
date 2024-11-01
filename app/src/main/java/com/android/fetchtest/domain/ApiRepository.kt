package com.android.fetchtest.domain

interface ApiRepository {
    suspend fun getItems(): List<Item>
}