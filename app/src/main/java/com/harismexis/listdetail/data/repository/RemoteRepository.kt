package com.harismexis.listdetail.data.repository

import android.util.Log
import com.harismexis.listdetail.data.api.ApiResponse
import com.harismexis.listdetail.data.api.Api
import kotlinx.coroutines.CancellationException

class RemoteRepository(private val api: Api) {

    private val tag = "RemoteRepository"

    sealed interface Result {
        data class Success(val response: ApiResponse) : Result
        data class Failure(val error: Throwable) : Result
    }

    suspend fun getCharacters(page: Int?): Result {
        runCatching {
            val response = api.getCharacters(page)
            Log.d(tag, "RickAndMortApi response: $response")
            return Result.Success(response)
        }.onFailure {
            if (it is CancellationException) {
                throw it
            }
            Log.d(tag, "Error fetching RickAndMortApi data: ${it.message ?: "Unknown error"}")
            return Result.Failure(it)
        }
        return Result.Failure(Throwable("Unknown error"))
    }
}