package com.organizer.data.remote.repo

import android.util.Log
import com.organizer.constants.AppConfig
import com.organizer.data.remote.ApiService
import jakarta.inject.Inject

class FileRemoteDataSource @Inject constructor(
    private val api: ApiService
) {

    suspend fun downloadFile(
        subfolder: String,
        fileName: String
    ): ByteArray? {
        return try {
            val url = "${AppConfig.API_BASE_URL}/$subfolder/$fileName"

            val response = api.downloadFile(url)

            if (response.isSuccessful) {
                response.body()?.bytes()
            } else {
                null
            }

        } catch (e: Exception) {
            Log.e("FILES", "Failed downloading image", e)
            null
        }
    }
}
