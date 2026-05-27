package com.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.organizer.presentation.OrganizerViewModel
import com.organizer.screens.SportsScreen
import com.organizer.screens.Subcategory
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

    val sports by viewModel.sportsUiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.Sports.route
    ) {

        composable(Routes.Sports.route) {

            SportsScreen(
                sports = sports,
                onSportClick = { sport ->

                    navController.navigate(
                        Routes.Subcategory.route
                    )
                }
            )
        }

        composable(Routes.Subcategory.route) {
            Subcategory()
        }
    }
}
