package com.example.venturotimezone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeZoneScreen()
        }
    }
}

@Composable
fun TimeZoneScreen() {
    val timeZone = remember { TimeZone.getDefault() }
    val timeFormatter = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }

    var currentTime by remember { mutableStateOf(timeFormatter.format(Date())) }

    // Update waktu setiap detik
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = timeFormatter.format(Date())
            kotlinx.coroutines.delay(1000L)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9)), // Warna latar belakang biru muda
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Welcome",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Current time zone: ${timeZone.displayName}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Current time: $currentTime",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
