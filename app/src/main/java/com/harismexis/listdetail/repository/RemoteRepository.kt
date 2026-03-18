package com.harismexis.listdetail.repository

import com.harismexis.listdetail.api.ApiResponse

interface RemoteRepository {
    suspend fun getRemoteData(): Result?
}

interface Result {
    data class Success(val response: ApiResponse) : Result
    data class Failure(val error: Throwable) : Result
}