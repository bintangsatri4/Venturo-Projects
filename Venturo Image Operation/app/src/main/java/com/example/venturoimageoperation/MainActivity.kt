package com.example.venturoimageoperation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.venturoimageoperation.screens.imageoperation.ImageOperationScreen
import com.example.venturoimageoperation.ui.theme.VenturoImageOperationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VenturoImageOperationTheme {
                ImageOperationScreen()
            }
        }
    }
}