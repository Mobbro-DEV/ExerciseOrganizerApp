package com.organizer.presentation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.local.db.entities.WorkoutEntity
import com.organizer.data.repo.CategoryRepository
import com.organizer.data.repo.ExerciseRepository
import com.organizer.data.repo.FileRepository
import com.organizer.data.repo.WorkoutExerciseRepository
import com.organizer.data.repo.WorkoutRepository
import com.organizer.presentation.screens.workouts_and_exercises.CustomsTab
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

@HiltViewModel
class OrganizerViewModel @Inject constructor(
    private val categoryRepo: CategoryRepository,
    private val exerciseRepo: ExerciseRepository,
    private val workoutRepo: WorkoutRepository,
    private val workoutExerciseRepo: WorkoutExerciseRepository,
    private val fileRepo: FileRepository,
) : ViewModel() {

    // General UI state
    val isSyncing = MutableStateFlow(false)

    var errorMessage: String? by mutableStateOf(null)
        private set

    val selectedTab = MutableStateFlow(CustomsTab.WORKOUTS)

    // Search
    val searchQuery = MutableStateFlow("")

    // Initialization
    init {
        syncDb()
    }

    fun syncDb() {
        viewModelScope.launch {
            isSyncing.value = true
            errorMessage = null
            try {
                categoryRepo.refreshSports()
                categoryRepo.refreshCategories()
                exerciseRepo.refreshExercises()
            } catch (e: IOException) {
                Log.e("SYNC", "No internet connection", e)
                errorMessage = "No internet connection. Please check your connection and try again."
            } catch (e: Exception) {
                Log.e("SYNC", "Sync failed", e)
                errorMessage = "Couldn't load data"
            } finally {
                isSyncing.value = false
            }
        }
    }

    // Categories
    private val selectedCategoryId = MutableStateFlow<Long?>(null)

    fun selectCategory(id: Long) {
        selectedCategoryId.value = id
    }

    val sportsUiState: StateFlow<List<CategoryEntity>> =
        combine(
            categoryRepo.observeSports(),
            searchQuery
        ) { sports, query ->
            if (query.isBlank()) sports
            else sports.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }.stateIn(viewModelScope,Sharing,emptyList()
        )

    val subcategoriesUiState: StateFlow<List<CategoryEntity>> =
        selectedCategoryId
            .filterNotNull()
            .flatMapLatest { id ->
                categoryRepo.observeSubcategories(id)
            }
            .stateIn(viewModelScope,Sharing,emptyList()
            )

    fun getCategoryPath(categoryId: Long): Flow<List<CategoryEntity>> = categoryRepo.getCategoryPath(categoryId)

    // Exercises
    private val selectedExerciseId = MutableStateFlow<Long?>(null)

    fun selectExercise(id: Long) {
        selectedExerciseId.value = id
    }

    val exerciseUiState: StateFlow<ExerciseEntity?> =
        selectedExerciseId
            .filterNotNull()
            .flatMapLatest { id ->
                exerciseRepo.observeExercise(id)
            }
            .stateIn(viewModelScope,Sharing,null)

    val exercisesByCategoryUiState: StateFlow<List<ExerciseEntity>> =
        selectedCategoryId
            .filterNotNull()
            .flatMapLatest { id ->
                exerciseRepo.observeExercisesByCategory(id)
            }
            .stateIn(viewModelScope,Sharing,emptyList())

    val customExercisesUiState: StateFlow<List<ExerciseEntity>> =
        exerciseRepo.observeCustomExercises()
            .stateIn(viewModelScope,Sharing,emptyList())

    // Workouts
    private val selectedWorkoutId = MutableStateFlow<Long?>(null)

    fun selectWorkout(id: Long) {
        selectedWorkoutId.value = id
    }

    val workoutsUiState: StateFlow<List<WorkoutEntity>> =
        workoutRepo.observeWorkouts()
            .stateIn(viewModelScope,Sharing,emptyList())

    val workoutUiState: StateFlow<WorkoutEntity?> =
        selectedWorkoutId
            .filterNotNull()
            .flatMapLatest { id ->
                workoutRepo.observeWorkoutById(id)
            }
            .stateIn(viewModelScope,Sharing,null)

    val exerciseIdsByWorkoutUiState: StateFlow<List<Long>> =
        selectedWorkoutId
            .filterNotNull()
            .flatMapLatest { id ->
                workoutExerciseRepo.observeExerciseIdsByWorkout(id)
            }
            .stateIn(viewModelScope,Sharing,emptyList())

    val workoutExercisesByIdsUiState: StateFlow<List<ExerciseEntity>> =
        exerciseIdsByWorkoutUiState
            .flatMapLatest { ids ->
                if (ids.isEmpty()) {
                    flowOf(emptyList())
                } else {
                    exerciseRepo.observeExercisesByIds(ids)
                        .map { exercises ->
                            val exerciseMap = exercises.associateBy { it.exerciseId }

                            ids.mapNotNull { id ->
                                exerciseMap[id]
                            }
                        }
                }
            }
            .stateIn(scope = viewModelScope,Sharing,initialValue = emptyList())

    // File operations
    fun getIconFile(name: String): File? {
        return fileRepo.getIcon(name)
    }

    fun getImageFile(name: String, isCustom: Boolean): File? {
        return fileRepo.getImage(name, isCustom)
    }

    fun saveCustomImage(uri: Uri): String? {
        return fileRepo.saveCustomImage(uri)
    }

    // Commands
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

    fun updateExerciseOrder(workoutId: Long, exercises: List<ExerciseEntity>) =
        viewModelScope.launch {
            workoutExerciseRepo.updateExerciseOrder(workoutId, exercises)
        }

    fun createCustomExercise(name: String, imageName: String) = viewModelScope.launch {
        exerciseRepo.addCustomExercise(name, imageName)
    }

    fun deleteCustomExercise(id: Long) = viewModelScope.launch {
        exerciseRepo.deleteCustomExercise(id)
    }

    companion object {
        val Sharing = SharingStarted.WhileSubscribed(5000)
    }
}
