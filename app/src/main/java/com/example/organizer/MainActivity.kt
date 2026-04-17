package com.example.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.organizer.data.entity.Exercise
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
        exercises.forEach {
            Column {
                Text("Exercise name: ${it.name}, Category: ${it.category}")

                Image(
                    painter = painterResource(id = it.imageRes),
                    contentDescription = it.name,
                    modifier = Modifier.size(150.dp)
                )

                Button(onClick = { viewModel.delete(it) }) {
                    Text(text = "delete")
                }
            }
        }
        AddCard(viewModel)
    }
}

@Composable
fun AddCard(viewModel: ViewModel) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        TextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") }
        )

        Button(
            onClick = {
                viewModel.addNew(
                    Exercise(
                        name = name,
                        imageRes = R.drawable.img,
                        category = category
                    )
                )
            }
        ) {
            Text("Add Person")
        }
    }
}
