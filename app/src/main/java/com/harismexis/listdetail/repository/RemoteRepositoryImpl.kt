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
import kotlin.coroutines.resumeWithException

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
                Log.d(tag, "Coroutine cancelled, cancelling the call")
                call.cancel()
            }
            call.enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Log.d(tag, e.message ?: "Unknown error")
                    resumeOnError(e.message)
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
                                Log.d(tag, "parsed response: $model")
                                if (continuation.isActive) {
                                    continuation.resume(value = Result.Success(model)) { _, _, _ -> }
                                }
                            } else {
                                val errorBody = it.body.string()
                                Log.d(tag, "error response: $errorBody")
                                resumeOnError(errorBody)
                            }
                        }
                    }.onFailure { error ->
                        Log.d(tag, error.message ?: "Unknown error")
                        resumeOnError(error.message)
                    }
                }

                private fun resumeOnError(error: String?) {
                    val message = error ?: "Unknown error"
                    if (continuation.isActive) {
                        continuation.resume(value = Result.Failure(Throwable(message))) { _, _, _ -> }
                    }
                }
            })
        }
    }
}