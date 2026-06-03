package com.organizer.data.repo

import com.organizer.constants.StorageFolders
import com.organizer.data.local.storage.FileStorage
import java.io.File
import javax.inject.Inject

class FileRepository @Inject constructor(
    private val fileStorage: FileStorage,
) {
    suspend fun downloadAndSaveIcons(iconNames: List<String?>) {
        fileStorage.downloadAndSaveFiles(
            StorageFolders.ICONS,
            iconNames.filterNotNull().filter { it.isNotBlank() }
        )
    }

    suspend fun downloadAndSaveImages(imageNames: List<String>) {
        fileStorage.downloadAndSaveFiles(
            StorageFolders.IMAGES,
            imageNames.filter { it.isNotBlank() }
        )
    }

    fun getIcon(name: String): File? {
        val file = File(fileStorage.dir(StorageFolders.ICONS), name)
        return file.takeIf { it.exists() }
    }

    fun getImage(name: String): File? {
        val file = File(fileStorage.dir(StorageFolders.IMAGES), name)
        return file.takeIf { it.exists() }
    }

    fun deleteIcon(iconName: String?) {
        if (!iconName.isNullOrBlank()) {
            fileStorage.deleteLocalFile(StorageFolders.ICONS, iconName)
        }
    }

    fun deleteImage(imageName: String) {
        if (imageName.isNotBlank()) {
            fileStorage.deleteLocalFile(StorageFolders.IMAGES, imageName)
        }
    }
}
