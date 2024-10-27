package com.example.quizy.data.common

import com.example.quizy.domain.CommonRepository
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    val sharedPrefsProvider : SharedPreferensesProvider
): CommonRepository {
    override suspend fun getUserIdFromSharedPrefs(): Int =
        sharedPrefsProvider.getIdFromSharedPrefs()


    override suspend fun setUserIdToSharedPrefs(id: Int) {
        sharedPrefsProvider.setIdToSharedPrefs(id)
    }
}