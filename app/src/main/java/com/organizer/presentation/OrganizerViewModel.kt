package com.organizer.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.repo.CategoryRepository
import com.organizer.data.repo.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class OrganizerViewModel @Inject constructor(
    private var categoryRepository: CategoryRepository,
    private var exerciseRepository: ExerciseRepository
) : ViewModel() {
    val categoriesUiState: StateFlow<List<CategoryEntity>> =
        categoryRepository.observeCategories()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val exercisesUiState: StateFlow<List<ExerciseEntity>> =
        exerciseRepository.observeExercises()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    init {
        viewModelScope.launch {
            syncDb()
        }
    }

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun syncDb() {
        viewModelScope.launch {
            try {
                categoryRepository.refreshCategories()
                exerciseRepository.refreshExercises()
            } catch (e: Exception) {
                errorMessage = "Could not refresh: ${e.message}"
            }
        }
    }
}
