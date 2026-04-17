package com.example.organizer.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.organizer.data.entity.Exercise
import com.example.organizer.data.OrganizerDatabase
import com.example.organizer.data.repo.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: ExerciseRepository
    val readAll: Flow<List<Exercise>>

    init {
        val db = OrganizerDatabase.get(application).dao
        repo = ExerciseRepository(db)
        readAll = repo.getAll()
    }

    fun addNew(exercise: Exercise) {
        viewModelScope.launch() {
            repo.insert(exercise)
        }
    }

    fun delete(exercise: Exercise) {
        viewModelScope.launch {
            repo.delete(exercise)
        }
    }
}
