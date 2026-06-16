package com.organizer.presentation.screens.sports

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun HeaderText() {
    Text(
        text = "Build Your\nOwn Workout!",
        fontSize = 34.sp,
        lineHeight = 38.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black,
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "What Would You Like To Do Today?",
        fontSize = 18.sp,
        color = Color.Gray
    )

    Spacer(modifier = Modifier.height(20.dp))
}
