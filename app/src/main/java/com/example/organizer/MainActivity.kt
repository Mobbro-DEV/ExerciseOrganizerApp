package com.example.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.organizer.local.OrganizerDatabase
import com.example.organizer.local.repo.CategoryRepository
import com.example.organizer.network.Api
import com.example.organizer.presentation.OrganizerViewModel
import com.example.organizer.presentation.OrganizerViewModelFactory

class MainActivity : ComponentActivity() {

    private val database by lazy { OrganizerDatabase.get(this) }

    private val repository by lazy {
        CategoryRepository(
            database.dao,
            Api.retrofitService
        )
    }

    private val viewModel: OrganizerViewModel by viewModels {
        OrganizerViewModelFactory(repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetAllCategories(viewModel)
        }
    }
}

@Composable
fun GetAllCategories(viewModel: OrganizerViewModel) {
    val categories by viewModel.categoriesUiState.collectAsState()

    Column(modifier = Modifier.padding(32.dp)) {
        Text("Size: ${categories.size}")
        categories.forEach {
            Text(it.name)
        }
    }
}
