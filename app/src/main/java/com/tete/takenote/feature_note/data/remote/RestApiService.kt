package com.tete.takenote.feature_note.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface RestApiService {

    @GET("/api/v1/entries/{word}")
    suspend fun getWordInfo(@Path("word") word: String): List<Nothing>

    @GET("SetPushToken") @Streaming
    suspend fun setPushTokenResponse(@Query("pushtoken") pushtoken: String): ResponseBody

    companion object {
        const val BASE_URL = "https://api.takenote.dev/"
    }
}