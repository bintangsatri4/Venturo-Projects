package com.example.venturoimageoperation

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

@Composable
fun VenturoImageOperationTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()
  val view = LocalView.current
  if (!view.isInEditMode) SideEffect { (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb() }
  MaterialTheme(colorScheme = colorScheme, content = content)
}