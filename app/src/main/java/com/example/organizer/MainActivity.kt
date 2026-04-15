package com.example.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.organizer.database.UserDatabase
import com.example.organizer.entity.User
import com.example.organizer.ui.theme.ExerciseOrganizerAppTheme
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            ExerciseOrganizerAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
//    val users = getAllUsers()
    val users = listOf("Bob", "Oll")

    Column(modifier = modifier) {
        for (user in users) {
            Text(text = "Hello $user!")
        }
    }
//    Text(
//        text = "Hello World",
//        modifier = modifier
//    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExerciseOrganizerAppTheme {
        Greeting("Android")
    }
}

@Composable
fun getAllUsers(): List<User> {
    val applicationContext = LocalContext.current

    val db = Room.databaseBuilder(
        applicationContext,
        UserDatabase::class.java, "user-db"
    ).build()
    val userDao = db.userDao()
    return userDao.getAll()
}