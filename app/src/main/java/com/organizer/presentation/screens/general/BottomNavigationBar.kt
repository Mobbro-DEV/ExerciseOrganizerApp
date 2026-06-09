package com.organizer.presentation.screens.general

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.organizer.presentation.Routes

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onHomeClick: () -> Unit,
    onWorkoutsClick: () -> Unit,
    onAddCardClick: () -> Unit,
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {

        NavigationBarItem(
            selected = currentRoute == Routes.Sports.route,
            onClick = onHomeClick,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Categories"
                )
            }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Workouts.route,
            onClick = onWorkoutsClick,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.FitnessCenter,
                    contentDescription = "Workouts"
                )
            }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.AddCard.route,
            onClick = onAddCardClick,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add Card"
                )
            }
        )
    }
}
