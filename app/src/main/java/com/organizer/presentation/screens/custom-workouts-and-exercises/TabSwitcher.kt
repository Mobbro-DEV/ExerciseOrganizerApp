package com.organizer.presentation.screens.`custom-workouts-and-exercises`

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TabSwitcher(
    selectedTab: CustomsTab,
    onTabSelected: (CustomsTab) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFF3EEEE))
            .padding(6.dp)
    ) {

        TabButton(
            title = "My Workouts",
            selected = selectedTab == CustomsTab.WORKOUTS,
            modifier = Modifier.weight(1f),
            onClick = { onTabSelected(CustomsTab.WORKOUTS) }
        )

        TabButton(
            title = "My Exercises",
            selected = selectedTab == CustomsTab.EXERCISES,
            modifier = Modifier.weight(1f),
            onClick = { onTabSelected(CustomsTab.EXERCISES) }
        )
    }
}
