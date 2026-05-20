package com.organizer.presentation

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
): ViewModel() {
    val categoriesUiState: StateFlow<List<CategoryEntity>> =
        repository.observeCategories()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun syncDb() {
        viewModelScope.launch {
            repository.refreshCategories()
        }
    }
}
