package com.organizer.presentation.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.presentation.OrganizerViewModel
import com.organizer.presentation.screens.exercises.ExerciseCard

@Composable
fun CategoryList(
    categoryId: Long,
    onCategoryClick: (CategoryEntity) -> Unit,
    viewModel: OrganizerViewModel
) {
    val categories by viewModel.getSubcategories(categoryId).collectAsState()
    val exercises by viewModel.getExercisesById(categoryId).collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        items(
            items = categories,
            key = { it.categoryId }
        ) { category ->
            CategoryCard(
                category = category,
                onClick = {
                    onCategoryClick(category)
                }
            )
        }

        items(
            items = exercises,
            key = { it.exerciseId }
        ) { exercise ->
            ExerciseCard(exercise)
        }
    }
}
