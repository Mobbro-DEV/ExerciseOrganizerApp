package com.organizer.presentation.screens.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.presentation.OrganizerViewModel
import com.organizer.presentation.screens.general.BottomNavigationBar

@Composable
fun CategoryContentScreen(
    categoryId: Long,
    onCategoryClick: (CategoryEntity) -> Unit,
    onBackClick: () -> Unit,
    viewModel: OrganizerViewModel = hiltViewModel()
) {
    val categoryPath by viewModel.getCategoryPath(categoryId).collectAsState(initial = emptyList())

    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        },
        containerColor = Color(0xFFF8F5F5)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            CategoryHeader(
                categoryPath.toMutableList(),
                onBackClick
            )

            Spacer(modifier = Modifier.height(28.dp))

            CategoryList(categoryId, onCategoryClick, viewModel)
        }
    }
}
