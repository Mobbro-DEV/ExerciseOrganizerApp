package com.example.organizer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserScreen(viewModel: UserViewModel) {

    val users by viewModel.users.collectAsState()

    Button(onClick = { viewModel.insertSampleData() }) {
        Text("Insert Sample Users")
    }

//    Column(modifier = Modifier.padding(16.dp)) {
//        users.forEach { user ->
//            Text("Hello ${user.firstName} ${user.lastName}")
//        }
//    }
}