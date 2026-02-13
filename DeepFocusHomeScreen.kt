package com.example.deepfocus

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DeepFocusHomeScreen() {

    // basic state (later can move to ViewModel)
    var isFocusing by remember { mutableStateOf(false) }
    var focusMinutes by remember { mutableStateOf(25) }
    var todayMinutes by remember { mutableStateOf(60) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // ───── Top: App title ─────
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "DeepFocus",
                fontSize = 28.sp
            )
            Text(
                text = "Focus Timer",
                fontSize = 14.sp
            )
        }

        // ───── Middle: Timer ─────
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$focusMinutes:00",
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = 0.0f,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // ───── Stats card ─────
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Today Focused")
                Text(
                    text = "$todayMinutes min",
                    fontSize = 20.sp
                )
            }
        }

        // ───── Action button ─────
        Button(
            onClick = { isFocusing = !isFocusing },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (isFocusing) "Pause" else "Start")
        }
    }
}
