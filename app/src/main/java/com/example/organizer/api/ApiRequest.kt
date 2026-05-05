package com.example.organizer.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class ApiRequest {

    val baseUrl = "http://10.0.2.2:8080/"
    private val client = OkHttpClient()
    private val gson = Gson()

    suspend fun healthCheck(): Int = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(baseUrl + "actuator/health")
            .build()

        client.newCall(request).execute().use { response ->
            response.code
        }
    }

    suspend fun getExerciseName(): String = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(baseUrl + "exercises/1") // adjust endpoint if needed
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("HTTP ${response.code}")
            }

            val json = response.body?.string()
                ?: throw Exception("Empty response")

            val jsonObject = gson.fromJson(json, JsonObject::class.java)

            jsonObject.get("name").asString
        }
    }
}
