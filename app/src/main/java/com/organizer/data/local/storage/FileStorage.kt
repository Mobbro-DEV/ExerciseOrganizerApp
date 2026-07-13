package com.organizer.data.local.storage

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.organizer.constants.AppConfig
import com.organizer.data.remote.repo.FileRemoteDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

class FileStorage @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fileRemoteDataSource: FileRemoteDataSource
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
            for (fileName in fileNames) {
                try {
                    val imageData = fileRemoteDataSource.downloadFile(subfolder, fileName)

                    imageData?.let {
                        withContext(Dispatchers.IO) {
                            File(dir(subfolder), fileName).writeBytes(imageData)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("FILES", "Failed downloading $fileName", e)
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

    fun deleteLocalFile(
        subfolder: String,
        fileName: String
    ) {
        File(dir(subfolder), fileName).delete()
    }
}
