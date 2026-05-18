package com.example.organizer.network.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.organizer.local.repo.CategoryRepository

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
