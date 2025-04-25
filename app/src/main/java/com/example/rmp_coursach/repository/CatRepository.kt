package com.example.rmp_coursach.repository

import android.util.Log
import com.example.rmp_coursach.data.CatDao
import com.example.rmp_coursach.model.Cat
import com.example.rmp_coursach.network.CatApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatRepository(
    private val catDao: CatDao,
    private val api: CatApi
) {
    val allCats = catDao.getAllCats()

    suspend fun fetchCats() {
        try {
            val response = api.getCats()
            if (response.isSuccessful && response.body() != null) {
                val cats = response.body()!!
                catDao.clearAll()
                catDao.insertAll(cats)
            } else {
                Log.e("CatRepository", "Ошибка: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("CatRepository", "Ошибка сети", e)
        }
    }
}
