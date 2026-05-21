package com.organizer.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.repo.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class OrganizerViewModel @Inject constructor(
    private var repository: CategoryRepository
) : ViewModel() {
    val categoriesUiState: StateFlow<List<CategoryEntity>> =
        repository.observeCategories()
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
                repository.refreshCategories()
            } catch (e: Exception) {
                errorMessage = "Could not refresh: ${e.message}"
            }
        }
    }
}
