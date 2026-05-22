package com.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.organizer.presentation.OrganizerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.forEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: OrganizerViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetAllCategories(viewModel)
        }
    }
}

@Composable
fun GetAllCategories(viewModel: OrganizerViewModel) {
    val categories by viewModel.categoriesUiState.collectAsState()
    val exercises by viewModel.exercisesUiState.collectAsState()
    val workouts by viewModel.workoutsUiState.collectAsState()
    val workoutExercises by viewModel.workoutExercisesUiState.collectAsState()

    Column(modifier = Modifier.padding(32.dp)) {
        viewModel.errorMessage?.let {
            Text(it)
        }

        // get all categories
        Text("Size: ${categories.size}")
        categories.forEach {
            Text(it.name)
        }

        // get all exercises
        Text("Size: ${exercises.size}")
        exercises.forEach {
            Text(it.name)
        }

        // refresh local data with api
        Button(
            onClick = { viewModel.syncDb() },
            modifier = Modifier.padding(16.dp)
        ) {

            Text("Refresh")
        }

        // create workout
        val workoutName = rememberTextFieldState()
        TextField(
            state = workoutName,
            placeholder = { Text("Workout name") }
        )

        Button(
            onClick = { viewModel.createWorkout(workoutName.text.toString()) },
            modifier = Modifier.padding(16.dp)
        ) {

            Text("Create workout")
        }

        // delete workout
        val workoutIdToDelete = rememberTextFieldState()
        TextField(
            state = workoutIdToDelete,
            placeholder = { Text("Workout id") }
        )

        Button(
            onClick = { viewModel.deleteWorkout(workoutIdToDelete.text.toString().toLong()) }
        ) {
            Text("Delete workout")
        }

        // get all workouts
        Text("Size: ${workouts.size}")
        workouts.forEach {
            Text(it.name)
        }

        // add exercise to workout
        val workoutId = rememberTextFieldState()
        TextField(
            state = workoutId,
            placeholder = { Text("Workout id") }
        )

        val exerciseId = rememberTextFieldState()
        TextField(
            state = exerciseId,
            placeholder = { Text("Exercise id") }
        )

        Button(
            onClick = {
                viewModel.addExerciseToWorkout(
                    workoutId.text.toString().toLong(),
                    exerciseId.text.toString().toLong()
                )
            },
            modifier = Modifier.padding(16.dp)
        ) {

            Text("Add to workout")
        }

        // get all exercises of workout
        Text("Size: ${workoutExercises.size}")
        workouts.forEach {
            Text(it.name)
        }

        // delete exercise from workout
        val workoutIdToDelete1 = rememberTextFieldState()
        TextField(
            state = workoutIdToDelete1,
            placeholder = { Text("Workout id") }
        )

        val exerciseIdToDelete = rememberTextFieldState()
        TextField(
            state = exerciseIdToDelete,
            placeholder = { Text("Exercise id") }
        )

        Button(
            onClick = {
                viewModel.deleteExerciseFromWorkout(
                    workoutIdToDelete1.text.toString().toLong(),
                    exerciseIdToDelete.text.toString().toLong()
                )
            }
        ) {
            Text("Delete workout")
        }
    }
}
