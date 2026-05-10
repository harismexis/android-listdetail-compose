package com.harismexis.listdetail.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import com.harismexis.listdetail.api.ApiResponse

class RemoteRepositoryImpl : RemoteRepository {

    private val tag = "RemoteRepositoryImpl"

    private val gson = Gson()
    private val client = OkHttpClient()

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getRemoteData(page: Int): Result {
        return suspendCancellableCoroutine { continuation ->
            val httpUrl: HttpUrl = HttpUrl.Builder()
                .scheme("https")
                .host("rickandmortyapi.com")
                .addPathSegment("api")
                .addPathSegment("character")
                .addQueryParameter("page", page.toString())
                .build()

            val request = Request.Builder()
                .url(httpUrl)
                .build()

            val call = client.newCall(request)
            continuation.invokeOnCancellation {
                call.cancel()
            }
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resume(value = Result.Failure(e)) { _, _, _ -> }
                }

                override fun onResponse(call: Call, response: Response) {
                    runCatching {
                        response.use {
                            if (it.isSuccessful) {
                                val bodyString = it.body.string()
                                Log.d(tag, "raw response: $bodyString")
                                val model: ApiResponse = gson.fromJson(
                                    bodyString,
                                    ApiResponse::class.java,
                                )
                                Log.d(tag, "parsed response: $response")
                                continuation.resume(value = Result.Success(model)) { _, _, _ -> }
                            } else {
                                val errorBody = it.body.string()
                                Log.d(tag, "error response: $errorBody")
                                continuation.resume(value = Result.Failure(Throwable(errorBody))) { _, _, _ -> }
                            }
                        }
                    }.onFailure { error ->
                        Log.d(tag, error.message ?: "Unknown error")
                        continuation.resume(value = Result.Failure(error)) { _, _, _ -> }
                    }
                }
            })
        }
    }
}