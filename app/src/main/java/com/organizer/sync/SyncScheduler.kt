package com.organizer.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.organizer.sync.worker.DataSyncWorker
import java.util.concurrent.TimeUnit

class SyncScheduler(private val context: Context) {

    fun scheduleDataSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<DataSyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "data_sync",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }
}
