package com.organizer.presentation.screens.workout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.presentation.OrganizerViewModel

@Composable
fun WorkoutContentScreen(
    workoutId: Long,
    onExerciseClick: (ExerciseEntity) -> Unit,
    onBackClick: () -> Unit,
    viewModel: OrganizerViewModel = hiltViewModel(),
) {
    LaunchedEffect(workoutId) {
        viewModel.selectWorkout(workoutId)
    }

    val workout by viewModel.workoutUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        WorkoutHeader(
            workout = workout,
            onBackClick = onBackClick
        )
//
//        Spacer(modifier = Modifier.height(28.dp))
//
//        CategoryList(categoryId, onCategoryClick, onExerciseClick, viewModel)
    }
}