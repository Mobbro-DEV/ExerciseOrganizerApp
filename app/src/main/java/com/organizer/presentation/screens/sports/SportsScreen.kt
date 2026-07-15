package com.organizer.presentation.screens.sports

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.presentation.OrganizerViewModel
import com.organizer.presentation.screens.categories.CategoryListItem
import com.organizer.presentation.screens.general.SearchBar

@Composable
fun SportsScreen(
    onSportClick: (CategoryEntity) -> Unit,
    viewModel: OrganizerViewModel = hiltViewModel(),
) {
    val sports by viewModel.sportsUiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val errorMessage = viewModel.errorMessage
    val isLoading by viewModel.isSyncing.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp)
    ) {

        HeaderText()

        SearchBar(
            query = searchQuery,
            onQueryChange = {
                viewModel.searchQuery.value = it
            }
        )

        // List of sport types
        if (sports.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingErrorView(
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    onRetry = { viewModel.syncDb() }
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(
                    items = sports,
                    key = { it.categoryId }
                ) { sport ->
                    CategoryListItem(
                        category = sport,
                        onClick = {
                            onSportClick(sport)
                        },
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
