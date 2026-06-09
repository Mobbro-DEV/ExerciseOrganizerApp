package com.organizer.presentation.screens.custom_workouts_and_exercises

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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.organizer.data.local.db.entities.WorkoutEntity
import com.organizer.presentation.OrganizerViewModel
import com.organizer.presentation.screens.workout.WorkoutList

@Composable
fun CustomWorkoutsAndExercisesScreen(
    selectedTab: CustomsTab,
    onTabSelected: (CustomsTab) -> Unit,
    onWorkoutClick: (WorkoutEntity) -> Unit,
    onCreateClick: () -> Unit,
    viewModel: OrganizerViewModel = hiltViewModel(),
) {
    val workouts by viewModel.workoutsUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {

        Spacer(Modifier.height(32.dp))

        TabSwitcher(
            selectedTab = selectedTab,
            onTabSelected = onTabSelected
        )

        Spacer(Modifier.height(28.dp))

        WorkoutList(
            workouts = workouts,
            onWorkoutClick = onWorkoutClick
        )

        Spacer(modifier = Modifier.weight(1f))

        CreateButton(
            selectedTab = selectedTab,
            onClick = onCreateClick,
            viewModel = viewModel
        )

        Spacer(Modifier.height(24.dp))
    }
}
