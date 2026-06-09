package com.organizer.data.local.storage

import android.content.Context
import android.util.Log
import com.organizer.constants.AppConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

class FileStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun dir(subfolder: String): File {
        val folder = File(context.filesDir, subfolder)
        if (!folder.exists()) folder.mkdirs()
        return folder
    }

    suspend fun downloadAndSaveFiles(
        subfolder: String,
        fileNames: List<String>
    ) {
        withContext(Dispatchers.IO) {
            val dir = dir(subfolder)

            for (fileName in fileNames) {
                try {
                    val url = URL("${AppConfig.API_BASE_URL}/$subfolder/$fileName")
                    val imageData = url.openStream().readBytes()

                    File(dir, fileName).writeBytes(imageData)
                } catch (e: Exception) {
                    Log.e("FILES", "Failed downloading $fileName", e)
                }
            }
        }
    }

    fun deleteLocalFile(subfolder: String, fileName: String) {
        val file = File(dir(subfolder), fileName)
        if (file.exists()) {
            file.delete()
        }
    }
}
