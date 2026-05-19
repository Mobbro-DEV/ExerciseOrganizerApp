package com.organizer.sync.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.organizer.data.repo.CategoryRepository

class CategorySyncWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: CategoryRepository
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            repository.syncCategories()
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}
