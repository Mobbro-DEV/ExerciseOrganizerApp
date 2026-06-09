package com.organizer.presentation.screens.custom_workouts_and_exercises

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.organizer.presentation.OrganizerViewModel

@Composable
fun CreateButton(
    selectedTab: CustomsTab,
    onClick: () -> Unit,
    viewModel: OrganizerViewModel
) {
    if (selectedTab == CustomsTab.WORKOUTS)
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = onClick,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Create New Workout")
        }
    else {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = { viewModel.createWorkout("test") },
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Create New Exercise")
        }
    }
}
