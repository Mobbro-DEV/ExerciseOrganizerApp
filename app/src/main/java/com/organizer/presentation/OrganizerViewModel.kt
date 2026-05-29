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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class OrganizerViewModel @Inject constructor(
    private val categoryRepo: CategoryRepository,
    private val exerciseRepo: ExerciseRepository,
    private val workoutRepo: WorkoutRepository,
    private val workoutExerciseRepo: WorkoutExerciseRepository,
) : ViewModel() {

    var errorMessage by mutableStateOf<String?>(null)
        private set

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

    fun getSubcategories(categoryId: Long): StateFlow<List<CategoryEntity>> {
        return categoryRepo.observeSubcategories(categoryId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    }

    fun getCategoryById(id: Long): StateFlow<CategoryEntity?> {
        return categoryRepo.observeCategoryById(id)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )
    }

    fun getCategoryPath(categoryId: Long): Flow<List<CategoryEntity>> = flow {
        val path = mutableListOf<CategoryEntity>()
        var current = categoryRepo.observeCategoryByIdOnce(categoryId)

        while (current != null) {
            path.add(current)
            current = current.parentCategoryId?.let {
                    categoryRepo.observeCategoryByIdOnce(it)
                }
        }
        emit(path.reversed())
    }

    val categoriesUiState: StateFlow<List<CategoryEntity>> =
        categoryRepo.observeCategories()
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

    fun getExercisesById(categoryId: Long): StateFlow<List<ExerciseEntity>> {
        return exerciseRepo.observeExercisesByCategory(categoryId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    }

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
