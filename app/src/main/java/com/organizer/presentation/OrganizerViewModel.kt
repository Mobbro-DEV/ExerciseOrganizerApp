package com.organizer.presentation

import android.util.Log
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
import com.organizer.data.repo.FileRepository
import com.organizer.data.repo.WorkoutExerciseRepository
import com.organizer.data.repo.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

@HiltViewModel
class OrganizerViewModel @Inject constructor(
    private val categoryRepo: CategoryRepository,
    private val exerciseRepo: ExerciseRepository,
    private val workoutRepo: WorkoutRepository,
    private val workoutExerciseRepo: WorkoutExerciseRepository,
    private val fileRepository: FileRepository,
) : ViewModel() {

    var errorMessage: String? by mutableStateOf(null)
        private set

    // SEARCH
    val searchQuery = MutableStateFlow("")

    init {
        syncDb()
    }

    fun syncDb() {
        viewModelScope.launch {
            try {
                categoryRepo.refreshCategories()
                exerciseRepo.refreshExercises()
            } catch (e: Exception) {
                Log.e("SYNC", "Sync failed", e)
                errorMessage = "Could not refresh: ${e.message}"
            }
        }
    }

    // STATIC STREAMS
    val categoriesUiState: StateFlow<List<CategoryEntity>> =
        categoryRepo.observeCategories()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val workoutsUiState: StateFlow<List<WorkoutEntity>> =
        workoutRepo.observeWorkouts()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val workoutExercisesUiState: StateFlow<List<WorkoutExerciseEntity>> =
        workoutExerciseRepo.observeWorkoutExercises()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customExercisesUiState: StateFlow<List<ExerciseEntity>> =
        exerciseRepo.observeCustomExercises()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // SPORTS (SEARCHABLE)
    val sportsUiState: StateFlow<List<CategoryEntity>> =
        combine(
            categoryRepo.observeSports(),
            searchQuery
        ) { sports, query ->
            if (query.isBlank()) sports
            else sports.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }

    // CATEGORY NAVIGATION STATE
    private val selectedCategoryId = MutableStateFlow<Long?>(null)

    fun selectCategory(id: Long) {
        selectedCategoryId.value = id
    }

    // SUBCATEGORIES (FIXED)
    val subcategoriesUiState: StateFlow<List<CategoryEntity>> =
        selectedCategoryId
            .filterNotNull()
            .flatMapLatest { id ->
                categoryRepo.observeSubcategories(id)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    // EXERCISE NAVIGATION STATE
    private val selectedExerciseId = MutableStateFlow<Long?>(null)

    fun selectExercise(id: Long) {
        selectedExerciseId.value = id
    }

    // EXERCISES
    val exerciseUiState: StateFlow<ExerciseEntity?> =
        selectedExerciseId
            .filterNotNull()
            .flatMapLatest { id ->
                exerciseRepo.observeExercise(id)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )

    val exercisesByCategoryUiState: StateFlow<List<ExerciseEntity>> =
        selectedCategoryId
            .filterNotNull()
            .flatMapLatest { id ->
                exerciseRepo.observeExercisesByCategory(id)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    // CATEGORY PATH
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

    // IMAGE STORAGE
    fun getIconFile(name: String): File? {
        return fileRepository.getIcon(name)
    }

    fun getImageFile(name: String): File? {
        return fileRepository.getImage(name)
    }

    // WORKOUT OPERATIONS
    fun createWorkout(name: String) = viewModelScope.launch {
        workoutRepo.createWorkout(name)
    }

    fun deleteWorkout(id: Long) = viewModelScope.launch {
        workoutRepo.deleteWorkout(id)
    }

    fun addExerciseToWorkout(workoutId: Long, exerciseId: Long) = viewModelScope.launch {
        workoutExerciseRepo.addExerciseToWorkout(workoutId, exerciseId)
    }

    fun deleteExerciseFromWorkout(workoutId: Long, exerciseId: Long) = viewModelScope.launch {
        workoutExerciseRepo.deleteExerciseFromWorkout(workoutId, exerciseId)
    }

    fun createCustomExercise(name: String) = viewModelScope.launch {
        exerciseRepo.addCustomExercise(name)
    }

    fun deleteCustomExercise(id: Long) = viewModelScope.launch {
        exerciseRepo.deleteCustomExercise(id)
    }
}
