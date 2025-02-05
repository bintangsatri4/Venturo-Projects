package com.venturo.venturoreport.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venturo.venturoreport.ui.theme.LightGray300
import com.venturo.venturoreport.ui.theme.Primary400

@Composable
fun HistoryBottomBar(
    modifier: Modifier = Modifier,
    price: Int = 0
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .fillMaxWidth()
            .background(color = LightGray300)
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = "Total",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "Rp $price",
                textAlign = TextAlign.Right,
                style = TextStyle(
                    color = Primary400,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

