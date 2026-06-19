package com.genesis.showroom.data.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

class GenesisApiException(
    message: String,
    val statusCode: Int? = null,
) : IOException(message)

class GenesisApiService(
    private val baseUrl: String,
    private val client: OkHttpClient = defaultClient(),
    private val json: Json = Json { ignoreUnknownKeys = true },
) {
    suspend fun sendChat(request: ChatRequest): ChatResponse = withContext(Dispatchers.IO) {
        val encodedBody = json.encodeToString(ChatRequest.serializer(), request)
        val httpRequest = Request.Builder()
            .url("$baseUrl/chat")
            .post(encodedBody.toRequestBody(JSON_MEDIA_TYPE))
            .build()

        execute(httpRequest, ChatResponse.serializer())
    }

    suspend fun captureLead(request: LeadRequest): LeadResponse = withContext(Dispatchers.IO) {
        val encodedBody = json.encodeToString(LeadRequest.serializer(), request)
        val httpRequest = Request.Builder()
            .url("$baseUrl/lead")
            .post(encodedBody.toRequestBody(JSON_MEDIA_TYPE))
            .build()

        execute(httpRequest, LeadResponse.serializer())
    }

    private fun <T> execute(
        request: Request,
        serializer: kotlinx.serialization.KSerializer<T>,
    ): T {
        client.newCall(request).execute().use { response ->
            val responseBody = response.body?.string().orEmpty()
            if (!response.isSuccessful) {
                val detail = runCatching {
                    json.decodeFromString(ApiErrorDetail.serializer(), responseBody).detail
                }.getOrNull()
                throw GenesisApiException(
                    message = detail ?: "Request failed (${response.code})",
                    statusCode = response.code,
                )
            }
            return json.decodeFromString(serializer, responseBody)
        }
    }

    companion object {
        private val JSON_MEDIA_TYPE = "application/json".toMediaType()

        fun defaultClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}
