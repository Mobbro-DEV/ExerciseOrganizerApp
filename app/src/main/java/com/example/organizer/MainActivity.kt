package com.example.organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.organizer.data.AppDatabase
import com.example.organizer.data.UserRepository
import com.example.organizer.ui.UserViewModel
import com.example.organizer.ui.theme.ExerciseOrganizerAppTheme
import com.example.organizer.ui.UserScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "user_database"
        ).build()

        val repository = UserRepository(db.userDao())
        val viewModel = UserViewModel(repository)

        setContent {
            ExerciseOrganizerAppTheme {
                UserScreen(viewModel)
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    val users = listOf("Bob", "Oll")
//    Column(modifier = modifier) {
//        for (user in users) {
//            Text(text = "Hello $user!")
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ExerciseOrganizerAppTheme {
//        Greeting("Android")
//    }
//}
