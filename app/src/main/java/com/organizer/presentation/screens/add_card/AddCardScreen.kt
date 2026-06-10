package com.organizer.presentation.screens.add_card

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.organizer.presentation.OrganizerViewModel

@Composable
fun AddCardScreen(
    onBackClick: () -> Unit = {},
    viewModel: OrganizerViewModel = hiltViewModel(),
) {
    var exerciseName by remember { mutableStateOf("") }

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
                text = "Create Your Exercise",
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Workout Name
        OutlinedTextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            placeholder = {
                Text("Name Of Exercise")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Save Button
        Button(
            onClick = {
                viewModel.createCustomExercise(exerciseName)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9C7AC7)
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .align(Alignment.CenterHorizontally)
                .height(56.dp)
        ) {
            Text(
                text = "Save",
                fontSize = 20.sp
            )
        }
    }
}
