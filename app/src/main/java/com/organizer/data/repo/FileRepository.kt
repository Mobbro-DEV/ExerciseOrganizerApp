package com.organizer.data.repo

import android.net.Uri
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

    fun saveCustomImage(uri: Uri): String? {
        return fileStorage.saveUriToStorage(StorageFolders.CUSTOM_IMAGE, uri)
    }

    fun getIcon(name: String): File? {
        val file = File(fileStorage.dir(StorageFolders.ICONS), name)
        return file.takeIf { it.exists() }
    }

    fun getImage(name: String, isCustom: Boolean): File? =
        File(
            fileStorage.dir(
                if (isCustom) StorageFolders.CUSTOM_IMAGE
                else StorageFolders.IMAGES
            ),
            name
        ).takeIf(File::exists)

    fun deleteIcon(iconName: String?) {
        if (!iconName.isNullOrBlank()) {
            fileStorage.deleteLocalFile(StorageFolders.ICONS, iconName)
        }
    }

    fun deleteImage(imageNames: List<String>) {
        for (name in imageNames) {
            if (name.isNotBlank()) {
                fileStorage.deleteLocalFile(StorageFolders.IMAGES, name)
            }
        }
    }
}
