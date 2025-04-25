package com.example.rmp_coursach.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmp_coursach.data.CatDatabase
import com.example.rmp_coursach.network.CatApi
import com.example.rmp_coursach.repository.CatRepository
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = CatDatabase.getDatabase(application).catDao()

    private val api = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CatApi::class.java)

    private val repository = CatRepository(dao, api)

    val cats = repository.allCats

    fun fetchCats() {
        viewModelScope.launch {
            repository.fetchCats()
        }
    }
}
