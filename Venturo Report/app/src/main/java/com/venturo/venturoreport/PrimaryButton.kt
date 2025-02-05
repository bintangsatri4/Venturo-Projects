package com.venturo.venturoreport

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    title: String,
    filled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (filled) Color.Blue else Color.Transparent,
            contentColor = if (filled) Color.White else Color.Blue
        )
    ) {
        Text(
            text = title,
            fontSize = 16.sp
        )
    }
}