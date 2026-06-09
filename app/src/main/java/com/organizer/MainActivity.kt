package com.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.organizer.presentation.Routes
import com.organizer.presentation.screens.addcard.AddCardScreen
import com.organizer.presentation.screens.sports.SportsScreen
import com.organizer.presentation.screens.categories.CategoryContentScreen
import com.organizer.presentation.screens.exercises.ExerciseCard
import com.organizer.presentation.screens.general.BottomNavigationBar
import com.organizer.presentation.screens.workouts.WorkoutsScreen
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
    val currentRoute = navController
        .currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route

    Scaffold(
        containerColor = Color(0xFFF8F5F5),
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onHomeClick = {
                    navController.navigate(Routes.Sports.route) {
                        popUpTo(Routes.Sports.route)
                        launchSingleTop = true
                    }
                },
                onWorkoutsClick = {
                    navController.navigate(Routes.Workouts.route)
                },
                onAddCardClick = {
                    navController.navigate(Routes.AddCard.route)
                }
            )
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Routes.Sports.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.Sports.route) {
                SportsScreen(
                    onSportClick = { category ->
                        navController.navigate(
                            Routes.Subcategory.createRoute(category.categoryId)
                        )
                    },
                )
            }

            composable(Routes.Subcategory.route) { backStackEntry ->
                val categoryId = backStackEntry.arguments
                    ?.getString("categoryId")
                    ?.toLongOrNull() ?: 0L

                CategoryContentScreen(
                    categoryId = categoryId,
                    onCategoryClick = { category ->
                        navController.navigate(
                            Routes.Subcategory.createRoute(category.categoryId)
                        )
                    },
                    onExerciseClick = { exercise ->
                        navController.navigate(
                            Routes.ExerciseCard.createRoute(exercise.exerciseId)
                        )
                    },
                    onBackClick = {
                        // TODO might be redundant
                        val currentRout = navController.currentDestination?.route
                        if (currentRout != Routes.Sports.route) {
                            navController.popBackStack()
                        }
                    },
                )
            }

            composable(Routes.ExerciseCard.route) { backStackEntry ->
                val exerciseId = backStackEntry.arguments
                    ?.getString("exerciseId")
                    ?.toLongOrNull() ?: 0L

                ExerciseCard(
                    exerciseId = exerciseId,
                    onSaveClick = {},
                    onBackClick = {
                        // TODO might be redundant
                        val currentRout = navController.currentDestination?.route
                        if (currentRout != Routes.Sports.route) {
                            navController.popBackStack()
                        }
                    },
                )
            }

            composable(Routes.Workouts.route) {
                WorkoutsScreen()
            }

            composable(Routes.AddCard.route) {
                AddCardScreen()
            }
        }
    }
}
