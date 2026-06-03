package com.organizer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.organizer.data.local.storage.FileStorage
import com.organizer.data.repo.CategoryRepository
import com.organizer.data.repo.ExerciseRepository
import com.organizer.data.repo.WorkoutExerciseRepository
import com.organizer.data.repo.WorkoutRepository

class OrganizerViewModelFactory(
    private val categoryRepo: CategoryRepository,
    private val exerciseRepo: ExerciseRepository,
    private val workoutRepo: WorkoutRepository,
    private val workoutExerciseRepo: WorkoutExerciseRepository,
    private val fileStorage: FileStorage,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrganizerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrganizerViewModel(categoryRepo, exerciseRepo, workoutRepo, workoutExerciseRepo, fileStorage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
