package com.example.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.example.organizer.data.Person
import com.example.organizer.presentation.ViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OutputDB(viewModel)
        }
    }
}

@Composable
fun OutputDB(viewModel: ViewModel) {
    val people by viewModel.readAll.collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(32.dp)) {
        people.forEach {
            Column {
                Text(text = "Hi, my name is ${it.name} - I am ${it.age} years old")
                Button(onClick = { viewModel.delete(it) }) {
                    Text(text = "delete")
                }
            }
        }
        AddNew(viewModel)
    }
}

@Composable
fun AddNew(viewModel: ViewModel) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") }
        )

        Button(
            onClick = {
                val ageInt = age.toIntOrNull() ?: 0

                viewModel.addNew(Person(name = name, age = ageInt))
            }
        ) {
            Text("Add Person")
        }
    }
}
