package com.venturo.venturofirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import com.google.firebase.analytics.FirebaseAnalytics
import com.venturo.venturofirebase.ui.theme.VenturoFirebaseTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        // Mengatur edge-to-edge
        enableEdgeToEdge()

        // Menampilkan konten
        setContent {
            VenturoFirebaseTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    GreetingScreen(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        firebaseAnalytics = firebaseAnalytics
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingScreen(
    name: String,
    modifier: Modifier = Modifier,
    firebaseAnalytics: FirebaseAnalytics
) {
    // Mengelola Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Text(
            text = "Hello $name!",
            modifier = modifier
                .padding(innerPadding)
                .clickable {
                    // Log Firebase Analytics event
                    val params = Bundle().apply {
                        putString(FirebaseAnalytics.Param.SCREEN_NAME, "Greeting Screen")
                        putString(FirebaseAnalytics.Param.SCREEN_CLASS, "GreetingScreen")
                    }
                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)

                    // Tampilkan Snackbar
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Firebase Analytics Event Logged!")
                    }
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VenturoFirebaseTheme {
        GreetingScreen(
            name = "Android",
            firebaseAnalytics = FirebaseAnalytics.getInstance(LocalContext.current)
        )
    }
}
