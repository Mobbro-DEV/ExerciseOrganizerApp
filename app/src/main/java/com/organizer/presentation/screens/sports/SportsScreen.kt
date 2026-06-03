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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.presentation.OrganizerViewModel
import com.organizer.presentation.screens.categories.CategoryListItem
import com.organizer.presentation.screens.general.BottomNavigationBar
import com.organizer.presentation.screens.general.SearchBar

@Composable
fun SportsScreen(
    onSportClick: (CategoryEntity) -> Unit,
    viewModel: OrganizerViewModel = hiltViewModel()
) {
    val sports by viewModel.sportsUiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val errorByLoading = viewModel.errorMessage

    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        },
        containerColor = Color(0xFFF8F5F5)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {

            HeaderText()

            SearchBar(
                query = searchQuery,
                onQueryChange = {
                    viewModel.searchQuery.value = it
                }
            )

            Spacer(modifier = Modifier.height(24.dp))


            if (sports.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading..",
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                }
            }
            // List of sport types
            if (sports.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorByLoading ?: "Loading..",
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
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
}
