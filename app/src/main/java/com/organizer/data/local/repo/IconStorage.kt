package com.organizer.data.local.repo

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

class IconStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun iconDir(): File {
        val dir = File(context.filesDir, "icons")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    suspend fun saveIcons(icons: List<String>) {
        withContext(Dispatchers.IO) {
            val dir = iconDir()

            for (iconName in icons) {
                val url = URL("http://10.0.2.2:8080/icons/$iconName")
                val imageData = url.openStream().readBytes()

                val file = File(dir, iconName)
                file.writeBytes(imageData)
            }
        }
    }

    fun deleteIcon(fileName: String) {
        val file = File(iconDir(), fileName)
        if (file.exists()) {
            file.delete()
        }
    }
}
