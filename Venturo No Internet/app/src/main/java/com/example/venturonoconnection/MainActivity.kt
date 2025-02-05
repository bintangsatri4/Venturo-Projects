package com.example.venturonoconnection

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import com.example.venturonoconnection.ui.theme.Purple40
import com.example.venturonoconnection.ui.theme.VenturoNoConnectionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            VenturoNoConnectionTheme {
                val isConnected = remember { mutableStateOf(NetworkUtil.isNetworkAvailable(this)) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isConnected.value) {
                        FastLoadingDataScreen(modifier = Modifier.padding(innerPadding))
                    } else {
                        NoInternetMessage(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun FastLoadingDataScreen(modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadUrl("https://google.com")
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun NoInternetMessage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.wifi_24px),
            contentDescription = "No Internet Connection",
            modifier = Modifier.size(128.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Purple40)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Oops Tidak Ada koneksi internet\n")
                }
                append("Pastikan wifi atau data seluler terhubung, lalu tekan tombol coba lagi")
            },
            color = Purple40,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VenturoNoConnectionTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun NoInternetMessagePreview() {
    VenturoNoConnectionTheme {
        NoInternetMessage()
    }
}