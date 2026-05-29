package com.organizer.presentation.screens.sports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.presentation.screens.categories.CategoryCard

@Composable
fun SportList(
    sports: List<CategoryEntity>,
    error: String?,
    onSportClick: (CategoryEntity) -> Unit
) {
    if (sports.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = error ?: "Loading..",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }

    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = sports,
                key = { it.categoryId }
            ) { sport ->
                CategoryCard(
                    category = sport,
                    onClick = {
                        onSportClick(sport)
                    }
                )
            }
        }
    }
}
