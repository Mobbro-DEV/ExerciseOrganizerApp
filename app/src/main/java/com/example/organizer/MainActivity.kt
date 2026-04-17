package com.example.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val exercises by viewModel.readAll.collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(32.dp)) {
        Button(onClick = {
            viewModel.addNew(
                name = "Push Ups",
                imageUrl = "https://images.squarespace-cdn.com/content/v1/57efdb4cd482e918dc2a900f/1f2f8b49-4ddc-4be3-bbea-c5d5046696b9/pushup.jpg?format=1000w",
                category = "Chest"
            )
        }) {
            Text("Add Exercise")
        }
        exercises.forEach {
            Column {
                Text("Exercise name: ${it.name}, Category: ${it.category}")

                AsyncImage(
                    model = it.imageUrl,
                    contentDescription = it.name
                )

                Button(onClick = { viewModel.delete(it) }) {
                    Text(text = "delete")
                }
            }
        }
    }
}
