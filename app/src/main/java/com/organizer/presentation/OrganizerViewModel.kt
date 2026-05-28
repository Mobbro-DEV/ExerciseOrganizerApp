package com.organizer.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.local.db.entities.WorkoutEntity
import com.organizer.data.local.db.entities.WorkoutExerciseEntity
import com.organizer.data.repo.CategoryRepository
import com.organizer.data.repo.ExerciseRepository
import com.organizer.data.repo.WorkoutExerciseRepository
import com.organizer.data.repo.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class OrganizerViewModel @Inject constructor(
    private val categoryRepo: CategoryRepository,
    private val exerciseRepo: ExerciseRepository,
    private val workoutRepo: WorkoutRepository,
    private val workoutExerciseRepo: WorkoutExerciseRepository,
) : ViewModel() {
    val categoriesUiState: StateFlow<List<CategoryEntity>> =
        categoryRepo.observeCategories()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val searchQuery = MutableStateFlow("")

    val sportsUiState: StateFlow<List<CategoryEntity>> =
        combine(
            categoryRepo.observeSports(),
            searchQuery
        ) { sports, query ->
            if (query.isBlank()) {
                sports
            } else {
                sports.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val selectedCategory = MutableStateFlow<CategoryEntity?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val subcategoriesUiState: StateFlow<List<CategoryEntity>> =
        selectedCategory
            .flatMapLatest { category ->
                if (category != null) {
                    categoryRepo.observeSubcategories(category.categoryId)
                } else {
                    flowOf(emptyList())
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val exercisesUiState: StateFlow<List<ExerciseEntity>> =
        exerciseRepo.observeExercises()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val workoutsUiState: StateFlow<List<WorkoutEntity>> =
        workoutRepo.observeWorkouts()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val workoutExercisesUiState: StateFlow<List<WorkoutExerciseEntity>> =
        workoutExerciseRepo.observeWorkoutExercises()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            syncDb()
        }
    }

    fun syncDb() {
        viewModelScope.launch {
            try {
                categoryRepo.refreshCategories()
                exerciseRepo.refreshExercises()
            } catch (e: Exception) {
                errorMessage = "Could not refresh: ${e.message}"
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }

    fun createWorkout(name: String) {
        viewModelScope.launch {
            workoutRepo.createWorkout(name)
        }
    }

    fun deleteWorkout(id: Long) {
        viewModelScope.launch {
            workoutRepo.deleteWorkout(id)
        }
    }

    fun addExerciseToWorkout(workoutId: Long, exerciseId: Long) {
        viewModelScope.launch {
            workoutExerciseRepo.addExerciseToWorkout(workoutId, exerciseId)
        }
    }

    fun deleteExerciseFromWorkout(workoutId: Long, exerciseId: Long) {
        viewModelScope.launch {
            workoutExerciseRepo.deleteExerciseFromWorkout(workoutId, exerciseId)
        }
    }

    fun addCustomExercise(name: String, imageUrl: String) {
        viewModelScope.launch {
            exerciseRepo.addCustomExercise(name, imageUrl)
        }
    }

    fun deleteCustomExercise(id: Long) {
        viewModelScope.launch {
            exerciseRepo.deleteCustomExercise(id)
        }
    }
}
