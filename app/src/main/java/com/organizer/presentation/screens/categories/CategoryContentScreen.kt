package com.organizer.presentation.screens.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.presentation.OrganizerViewModel
import com.organizer.presentation.screens.general.BottomNavigationBar
import com.organizer.presentation.screens.general.ItemList

@Composable
fun CategoryContentScreen(
    categoryId: Long,
    onCategoryClick: (CategoryEntity) -> Unit,
    onBackClick: () -> Unit,
    viewModel: OrganizerViewModel = hiltViewModel()
) {
    // TODO: make it work so : null → loading, empty list → no content, list with items → show UI content
    // initial = null means we treat "null" as loading state before data arrives.
//    val categoriesState = viewModel.getSubcategories(categoryId)
//        .collectAsState(initial = null)
    // Extracts the actual list value from the State wrapper.
    // This gives us a nullable List<CategoryEntity>:
    // - null → still loading
    // - empty list → no content
    // - list with items → show UI content
//    val categories = categoriesState.value
    val categories by viewModel.getSubcategories(categoryId).collectAsState()
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

            if (categories == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading..",
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                }
            }
            // List of categories
            else {
                ItemList(
                    items = categories,
                    key = { it.categoryId },
                    message = "NoContent"
                ) { category ->

                    CategoryCard(
                        category = category,
                        onClick = {
                            onCategoryClick(category)
                        }
                    )
                }
            }
        }
    }
}
