package com.organizer.presentation.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.presentation.OrganizerViewModel

@Composable
fun WorkoutContentScreen(
    workoutId: Long,
    onOpenExerciseClick: (ExerciseEntity) -> Unit,
    onBackClick: () -> Unit,
    viewModel: OrganizerViewModel = hiltViewModel(),
) {
    LaunchedEffect(workoutId) {
        viewModel.selectWorkout(workoutId)
    }

    val workout by viewModel.workoutUiState.collectAsState()
    val exercises by viewModel.workoutExercisesByIdsUiState.collectAsState()
    var expandedExerciseId by remember { mutableStateOf<Long?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        WorkoutHeader(
            workout = workout,
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(28.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            items(
                items = exercises,
                key = { it.exerciseId }
            ) { exercise ->
                WorkoutExerciseListItem(
                    exercise = exercise,
                    expanded = expandedExerciseId == exercise.exerciseId,
                    onClick = {
                        expandedExerciseId =
                            if (expandedExerciseId == exercise.exerciseId)
                                null
                            else
                                exercise.exerciseId
                    },
                    onOpenClick = { onOpenExerciseClick(exercise) },
                    onDeleteClick = {
                        viewModel.deleteExerciseFromWorkout(
                            workoutId,
                            exercise.exerciseId
                        )
                    },
                    viewModel = viewModel
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                viewModel.deleteWorkout(workoutId)
                onBackClick()
                      },
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Delete Workout")
        }
    }
}
