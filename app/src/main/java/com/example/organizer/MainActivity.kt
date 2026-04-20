package com.example.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.organizer.presentation.ViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExerciseCard(viewModel)
        }
    }
}

@Composable
fun ExerciseCard(viewModel: ViewModel) {

    LaunchedEffect(Unit) {
        viewModel.fetchApiData()
    }

    val apiData = viewModel.apiData

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(apiData) { item ->

            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Category: ${item.category}",
                    style = MaterialTheme.typography.bodyMedium
                )

                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.name,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(200.dp)
                )
            }
        }
    }
}
