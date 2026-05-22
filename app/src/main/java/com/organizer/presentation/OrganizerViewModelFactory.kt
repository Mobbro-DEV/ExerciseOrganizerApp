package com.organizer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.organizer.data.repo.CategoryRepository
import com.organizer.data.repo.ExerciseRepository

class OrganizerViewModelFactory(
    private val categoryRepository: CategoryRepository,
    private val exerciseRepository: ExerciseRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrganizerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrganizerViewModel(categoryRepository, exerciseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
