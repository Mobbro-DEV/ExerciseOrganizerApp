package com.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.organizer.presentation.Routes
import com.organizer.presentation.screens.sports.SportsScreen
import com.organizer.presentation.screens.categories.SubcategoriesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Sports.route
    ) {
        composable(Routes.Sports.route) {
            SportsScreen(
                onSportClick = { category ->
                    navController.navigate(
                        Routes.Subcategory.createRoute(category.categoryId)
                    )
                }
            )
        }

        composable(Routes.Subcategory.route) { backStackEntry ->
            val categoryId = backStackEntry.arguments
                    ?.getString("categoryId")
                    ?.toLongOrNull()

            SubcategoriesScreen(
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
