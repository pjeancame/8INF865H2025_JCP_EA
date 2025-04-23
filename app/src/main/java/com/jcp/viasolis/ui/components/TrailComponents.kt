package com.jcp.viasolis.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Composable pour afficher l'indicateur de difficulté avec 4 rectangles remplis
@Composable
fun DifficultyIndicator(difficulty: Int) {
    Row {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .size(20.dp, 10.dp)
                    .padding(end = 4.dp)
                    .background(if (index < difficulty) Color.Red else Color.Gray)
            )
        }
    }
}

@Composable
fun InfoBox(label: String, value: @Composable () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        value() // Ici, on appelle directement le composable
    }
}

@Composable
fun InfoBoxText(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            maxLines = 1,
            softWrap = false
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }
}
