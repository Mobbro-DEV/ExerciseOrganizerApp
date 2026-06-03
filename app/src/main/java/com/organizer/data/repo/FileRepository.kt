package com.organizer.data.repo

import com.organizer.data.local.storage.FileStorage
import java.io.File
import javax.inject.Inject

class FileRepository @Inject constructor(
    private val fileStorage: FileStorage,
) {
    suspend fun downloadAndSaveIcons(iconNames: List<String?>) {
        fileStorage.downloadAndSaveFiles(
            "icons",
            iconNames.filterNotNull().filter { it.isNotBlank() }
        )
    }
    // TODO add an enum for subfolder
    suspend fun downloadAndSaveImages(imageNames: List<String>) {
        fileStorage.downloadAndSaveFiles(
            "images",
            imageNames.filter { it.isNotBlank() }
        )
    }

    fun getIcon(name: String): File {
        return File(fileStorage.dir("icons"), name)
    }

    fun getImage(name: String): File {
        return File(fileStorage.dir("images"), name)
    }

    fun deleteIcon(iconName: String?) {
        if (!iconName.isNullOrBlank()) {
            fileStorage.deleteLocalFile("icons", iconName)
        }
    }

    fun deleteImage(imageName: String) {
        if (imageName.isNotBlank()) {
            fileStorage.deleteLocalFile("images", imageName)
        }
    }
}
