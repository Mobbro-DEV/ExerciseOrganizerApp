package com.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.organizer.presentation.OrganizerViewModel
import com.organizer.screens.SportsScreen
import com.organizer.screens.SubcategoriesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: OrganizerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main(viewModel)
        }
    }
}

@Composable
fun Main(viewModel: OrganizerViewModel){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Sports.route
    ) {
        composable(Routes.Sports.route) {
            SportsScreen(
                viewModel = viewModel,
                onSportClick = { category ->
                    navController.navigate(
                        Routes.Subcategory.createRoute(category.categoryId)
                    )
                }
            )
        }

        composable(Routes.Subcategory.route) { backStackEntry ->
            val categoryId =
                backStackEntry.arguments
                    ?.getString("categoryId")
                    ?.toLongOrNull()

            SubcategoriesScreen(
                viewModel = viewModel,
                categoryId = categoryId ?: 0L,
                onCategoryClick = { category ->
                    navController.navigate(
                        Routes.Subcategory.createRoute(category.categoryId)
                    )
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
