package com.harismexis.listdetail.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int?
    ): ApiResponse
}