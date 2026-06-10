package com.organizer.presentation.screens.workouts_and_exercises

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.local.db.entities.WorkoutEntity
import com.organizer.presentation.OrganizerViewModel
import com.organizer.presentation.screens.exercises.ExerciseListItem
import com.organizer.presentation.screens.workout.WorkoutListItem
import androidx.compose.ui.Modifier

@Composable
fun CustomsList(
    selectedTab: CustomsTab,
    onWorkoutClick: (WorkoutEntity) -> Unit,
    onExerciseClick: (ExerciseEntity) -> Unit,
    onCreateWorkoutClick: () -> Unit,
    onCreateExerciseClick: () -> Unit,
    viewModel: OrganizerViewModel = hiltViewModel(),
) {
    val workouts by viewModel.workoutsUiState.collectAsState()
    val customExercises by viewModel.customExercisesUiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            when (selectedTab) {
                CustomsTab.WORKOUTS -> {
                    items(
                        items = workouts,
                        key = { it.workoutId }
                    ) { workout ->
                        WorkoutListItem(
                            title = workout.name,
                            onClick = { onWorkoutClick(workout) }
                        )
                    }
                }

                CustomsTab.EXERCISES -> {
                    items(
                        items = customExercises,
                        key = { it.exerciseId }
                    ) { exercise ->
                        ExerciseListItem(
                            exercise = exercise,
                            onClick = { onExerciseClick(exercise) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {
            CustomsTab.WORKOUTS -> {
                CreateButton(
                    text = "Create New Workout",
                    onClick = onCreateWorkoutClick
                )
            }

            CustomsTab.EXERCISES -> {
                CreateButton(
                    text = "Create New Exercise",
                    onClick = onCreateExerciseClick
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun CreateButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text)
    }
}
