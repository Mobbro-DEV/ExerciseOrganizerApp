package com.example.organizer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.organizer.local.entity.CategoryEntity
import com.example.organizer.local.repo.CategoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrganizerViewModel(
    repository: CategoryRepository
): ViewModel() {
    val categoriesUiState: StateFlow<List<CategoryEntity>> =
        repository.getAll()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    init {
        viewModelScope.launch {
            repository.syncCategories()
        }
    }
}
