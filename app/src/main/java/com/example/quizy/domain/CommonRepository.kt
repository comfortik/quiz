package com.example.quizy.domain

interface CommonRepository {
    suspend fun getUserIdFromSharedPrefs(): Int
    suspend fun setUserIdToSharedPrefs(id: Int)
}