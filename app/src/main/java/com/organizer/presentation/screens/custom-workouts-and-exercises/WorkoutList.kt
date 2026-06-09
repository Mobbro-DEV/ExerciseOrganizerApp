package com.organizer.presentation.screens.`custom-workouts-and-exercises`

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.organizer.data.local.db.entities.WorkoutEntity

@Composable
fun WorkoutList(
    workouts: List<WorkoutEntity>,
    onWorkoutClick: (WorkoutEntity) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        items(workouts) { workout ->

            WorkoutListItem(
                title = workout.name,
                onClick = {
                    onWorkoutClick(workout)
                }
            )
        }
    }
}
