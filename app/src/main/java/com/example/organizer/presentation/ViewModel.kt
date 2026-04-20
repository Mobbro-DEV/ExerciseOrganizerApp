package com.example.organizer.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.organizer.api.requests.Wger
import com.example.organizer.data.entity.ExerciseEntity
import com.example.organizer.data.OrganizerDatabase
import com.example.organizer.data.repo.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: ExerciseRepository
    val readAll: Flow<List<ExerciseEntity>>
    private val _apiData = mutableStateOf<List<ExerciseEntity>>(emptyList())
    val apiData: List<ExerciseEntity> get() = _apiData.value

    init {
        val db = OrganizerDatabase.get(application).dao
        repo = ExerciseRepository(db)
        readAll = repo.getAll()
    }

    fun addNew(name: String, imageUrl: String, category: String) {
        viewModelScope.launch() {
            repo.insert(
                ExerciseEntity(
                    name = name,
                    imageUrl = imageUrl,
                    category = category
                )
            )
        }
    }

    fun delete(exercise: ExerciseEntity) {
        viewModelScope.launch {
            repo.delete(exercise)
        }
    }

    fun fetchApiData() {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                Wger().getExerciseCard()
            }
            _apiData.value = data
        }
    }
}
