package com.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.organizer.presentation.OrganizerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.forEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: OrganizerViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetAllCategories(viewModel)
        }
    }
}

@Composable
fun GetAllCategories(viewModel: OrganizerViewModel) {
// runs once when the composable enters the screen
    LaunchedEffect(Unit) {
        viewModel.syncDb()
    }

    val categories by viewModel.categoriesUiState.collectAsState()

    Column(modifier = Modifier.padding(32.dp)) {
        Text("Size: ${categories.size}")
        categories.forEach {
            Text(it.name)
        }

        Button(
            onClick = { viewModel.syncDb() },
            modifier = Modifier.padding(16.dp)) {

            Text("Refresh")
        }
    }
}
