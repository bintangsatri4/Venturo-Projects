package com.venturo.venturoreport.ui.screens.order.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venturo.venturoreport.ui.theme.Primary400

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    imageId: Int,
    title: String,
    isHighlight: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(28.dp),
            colorFilter = ColorFilter.tint(
                color = Primary400,
                blendMode = BlendMode.SrcIn,
            ),
            painter = painterResource(id = imageId),
            contentScale = ContentScale.Fit,
            contentDescription = title,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = if (isHighlight) Color.Black else Primary400
        )
    }
}