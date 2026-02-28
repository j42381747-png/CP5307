package com.example.deepfocus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    DeepFocusApp()
                }
            }
        }
    }
}

// --- Data Class for Posts ---
data class FocusPost(
    val id: Int,
    val userName: String,
    val focusDurationMinutes: Int,
    val thoughts: String,
    val timestamp: String,
    val location: String? = null
)

sealed class Screen(val title: String) {
    object Home : Screen("Home")
    object Focus : Screen("Focus")
    object Settings : Screen("Settings")
}

@Composable
fun DeepFocusApp() {
    var currentScreen: Screen by remember { mutableStateOf(Screen.Home) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(Screen.Home, Screen.Focus, Screen.Settings)
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            when (screen) {
                                Screen.Home -> Icon(Icons.Filled.Home, contentDescription = screen.title)
                                Screen.Focus -> Icon(Icons.Filled.HourglassEmpty, contentDescription = screen.title)
                                Screen.Settings -> Icon(Icons.Filled.Settings, contentDescription = screen.title)
                            }
                        },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen }
                    )
                }
            }
        },
        floatingActionButton = {
            if (currentScreen == Screen.Home) {
                FloatingActionButton(
                    onClick = { /* TODO: Handle FAB click for new post */ },
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "New Post", tint = Color.Black)
                }
            }
        }
    ) { innerPadding ->
        when (currentScreen) {
            Screen.Home -> HomeScreen(modifier = Modifier.padding(innerPadding))
            Screen.Focus -> FocusScreen(modifier = Modifier.padding(innerPadding))
            Screen.Settings -> SettingsScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun PostCard(post: FocusPost, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Avatar and Name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Avatar",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = post.userName, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(12.dp))

            // Content: Duration and Thoughts
            Text(
                text = "Focused for ${post.focusDurationMinutes} minutes",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.thoughts, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(12.dp))

            // Footer: Timestamp and Location
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.align(Alignment.BottomEnd)) {
                    Text(
                        text = post.timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    post.location?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // Sample data for demonstration
    val posts = remember {
        listOf(
            FocusPost(1, "Alice", 45, "Great session! Really got into the flow.", "2 hours ago", "New York"),
            FocusPost(2, "Bob", 25, "Felt a bit distracted today, but managed to finish my task.", "5 hours ago"),
            FocusPost(3, "Charlie", 75, "Long session, but worth it. The new feature is almost complete!", "Yesterday", "San Francisco"),
            FocusPost(4, "Diana", 50, "Just finished a pomodoro session. Feeling productive!", "Yesterday")
        )
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(posts) { post ->
            PostCard(post = post, modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings", fontSize = 24.sp)
    }
}


@Composable
fun FocusScreen(modifier: Modifier = Modifier) {

    // basic state (later can move to ViewModel)
    val focusDurationMinutes = 25
    var timeLeftInSeconds by remember { mutableIntStateOf(focusDurationMinutes * 60) }
    var isFocusing by remember { mutableStateOf(false) }
    var todayMinutes by remember { mutableIntStateOf(60) }

    // Countdown logic
    LaunchedEffect(key1 = isFocusing, key2 = timeLeftInSeconds) {
        if (isFocusing && timeLeftInSeconds > 0) {
            delay(1000L)
            timeLeftInSeconds--
        } else if (isFocusing && timeLeftInSeconds == 0) {
            isFocusing = false // Stop the timer when it reaches 0
        }
    }

    // UI display values
    val minutes = timeLeftInSeconds / 60
    val seconds = timeLeftInSeconds % 60
    val timeDisplay = String.format("%02d:%02d", minutes, seconds)
    val progress = { 1f - timeLeftInSeconds.toFloat() / (focusDurationMinutes * 60) }
    val buttonText = when {
        isFocusing -> "Pause"
        timeLeftInSeconds < focusDurationMinutes * 60 && timeLeftInSeconds > 0 -> "Resume"
        else -> "Start"
    }

    Column(
        modifier = modifier
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
                text = timeDisplay,
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = progress,
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
            onClick = {
                if (timeLeftInSeconds == 0) { // If timer finished, reset it
                    timeLeftInSeconds = focusDurationMinutes * 60
                }
                isFocusing = !isFocusing
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = buttonText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        DeepFocusApp()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}
