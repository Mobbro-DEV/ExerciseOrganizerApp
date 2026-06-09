package com.organizer.presentation.screens.`custom-workouts-and-exercises`

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateWorkoutButton(
    selectedTab: CustomsTab,
    onClick: () -> Unit
) {

    val text =
        if (selectedTab == CustomsTab.WORKOUTS)
            "Create New Exercise"
        else
            "Create New Workout"

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
