package com.organizer.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.organizer.presentation.OrganizerViewModel

@Composable
fun Subcategory(viewModel: OrganizerViewModel) {
    val subcategories by viewModel.subcategoriesUiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(64.dp)
    ) {
        items(subcategories) { category ->
            Text(category.name)
        }
    }
}
