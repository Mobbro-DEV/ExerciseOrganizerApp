package com.organizer.data.local.storage

import android.content.Context
import android.net.Uri
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

    // saves file from uri to device storage and returns its name
    fun saveUriToStorage(
        subfolder: String,
        uri: Uri,
    ): String? {
        return try {
            val dir = dir(subfolder)
            val fileName = "image_${System.currentTimeMillis()}.jpg"
            val destinationFile = File(dir, fileName)

            context.contentResolver.openInputStream(uri)?.use { input ->
                destinationFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            fileName
        } catch (e: Exception) {
            Log.e("FILES", "Failed saving image", e)
            null
        }
    }

    fun deleteLocalFile(subfolder: String, fileName: String) {
        val file = File(dir(subfolder), fileName)
        if (file.exists()) {
            file.delete()
        }
    }
}
