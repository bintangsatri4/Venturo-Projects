package com.venturo.venturolistitem.ui.components.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.venturo.venturolistitem.ui.theme.Danger400
import com.venturo.venturolistitem.ui.theme.White
import com.venturo.venturolistitem.ui.theme.Transparent

@Composable
fun SwipeBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Danger400
        SwipeToDismissBoxValue.EndToStart -> Danger400
        SwipeToDismissBoxValue.Settled -> Transparent
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "delete",
            tint = White,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "delete",
            tint = White,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
fun SwipeBackgroundPreview() {
    SwipeBackground(rememberSwipeToDismissBoxState())
}

