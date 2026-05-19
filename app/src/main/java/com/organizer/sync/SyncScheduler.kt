package com.organizer.sync

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.organizer.sync.worker.CategorySyncWorker
import java.util.concurrent.TimeUnit

class SyncScheduler(private val context: Context) {

    fun scheduleCategorySync() {
        val request = PeriodicWorkRequestBuilder<CategorySyncWorker>(
            2, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "category_sync",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }
}
