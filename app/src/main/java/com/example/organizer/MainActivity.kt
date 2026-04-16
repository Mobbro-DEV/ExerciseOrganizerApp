package com.example.organizer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.organizer.data.Person
import com.example.organizer.data.PersonDao
import com.example.organizer.data.PersonDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = PersonDatabase.get(this)
        val dao = db.dao

        //Insert
        dao.insert(
            Person(name = "Alice", age = 25),
            Person(name = "Bob", age = 30)
        )

        setContent {
            OutputDB(dao)
        }
    }
}

@Composable
fun OutputDB(dao: PersonDao) {
    val people by dao.getPeople().collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        people.forEach {
            Text(text = "${it.name} - ${it.age}")
        }
    }
}