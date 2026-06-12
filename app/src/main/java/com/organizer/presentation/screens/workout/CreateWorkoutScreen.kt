package com.organizer.presentation.screens.workout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.organizer.presentation.OrganizerViewModel
import com.organizer.presentation.screens.general.SaveButton

@Composable
fun CreateWorkoutScreen(
    onBackClick: () -> Unit = {},
    viewModel: OrganizerViewModel = hiltViewModel(),
) {
    var workoutName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Create Your Workout",
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Workout Name
        OutlinedTextField(
            value = workoutName,
            onValueChange = { workoutName = it },
            placeholder = {
                Text("Name Of Workout")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        SaveButton(
            onClick = {
                viewModel.createWorkout(workoutName)
                onBackClick()
            },
        )
    }
}
