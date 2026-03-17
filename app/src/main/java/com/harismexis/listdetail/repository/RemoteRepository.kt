package com.harismexis.listdetail.repository

import com.harismexis.listdetail.api.ApiResponse

interface RemoteRepository {
    suspend fun getRemoteData(): ApiResponse?
}