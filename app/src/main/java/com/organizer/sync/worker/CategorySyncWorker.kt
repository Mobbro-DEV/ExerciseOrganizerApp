package com.organizer.sync.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.organizer.presentation.OrganizerViewModel

class CategorySyncWorker(
    context: Context,
    params: WorkerParameters,
    private val viewModel: OrganizerViewModel
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            viewModel.syncDb()
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}
