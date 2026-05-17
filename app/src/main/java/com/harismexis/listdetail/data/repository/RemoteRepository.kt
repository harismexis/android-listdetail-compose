package com.harismexis.listdetail.data.repository

import com.harismexis.listdetail.data.api.ApiResponse

interface RemoteRepository {

    sealed interface Result {
        data class Success(val response: ApiResponse) : Result
        data class Failure(val error: Throwable) : Result
    }
    suspend fun getRemoteData(page: Int = 0): Result?
}

