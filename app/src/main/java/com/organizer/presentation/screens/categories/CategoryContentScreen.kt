package com.organizer.presentation.screens.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.presentation.OrganizerViewModel

@Composable
fun CategoryContentScreen(
    categoryId: Long,
    onCategoryClick: (CategoryEntity) -> Unit,
    onExerciseClick: (ExerciseEntity) -> Unit,
    onBackClick: () -> Unit,
    viewModel: OrganizerViewModel,
) {
    val categoryPath by viewModel.getCategoryPath(categoryId).collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp)
    ) {

        CategoryHeader(
            categoryPath.toMutableList(),
            onBackClick,
            viewModel
        )

        Spacer(modifier = Modifier.height(28.dp))

        CategoryList(categoryId, onCategoryClick, onExerciseClick, viewModel)
    }
}
