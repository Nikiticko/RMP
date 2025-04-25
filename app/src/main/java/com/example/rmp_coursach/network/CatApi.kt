package com.example.rmp_coursach.network

import com.example.rmp_coursach.model.Cat
import retrofit2.Response
import retrofit2.http.GET

interface CatApi {
    @GET("v1/images/search?limit=10")
    suspend fun getCats(): Response<List<Cat>>
}
