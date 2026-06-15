package com.organizer.presentation.screens.add_card

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.organizer.presentation.OrganizerViewModel
import com.organizer.presentation.screens.general.SaveButton

@Composable
fun AddCardScreen(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: OrganizerViewModel = hiltViewModel(),
) {
    var exerciseName by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val canSave = exerciseName.isNotBlank()
    var isSaving by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Create Your Exercise",
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Workout Name
        OutlinedTextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            placeholder = {
                Text("Name Of Exercise")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Add Image
        AddImageField(
            imageUri = imageUri,
            onImageSelected = { uri ->
                imageUri = uri
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Save Button
        SaveButton(
            enabled = canSave && !isSaving,
            onClick = {
                isSaving = true
                viewModel.createCustomExercise(
                    name = exerciseName.trim(),
                    uri = imageUri
                )
                onSaveClick()
            }
        )
    }
}
