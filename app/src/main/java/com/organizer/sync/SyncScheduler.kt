package com.organizer.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.organizer.sync.worker.CategorySyncWorker
import java.util.concurrent.TimeUnit

class SyncScheduler(private val context: Context) {

    fun scheduleCategorySync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<CategorySyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "category_sync",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }
}
