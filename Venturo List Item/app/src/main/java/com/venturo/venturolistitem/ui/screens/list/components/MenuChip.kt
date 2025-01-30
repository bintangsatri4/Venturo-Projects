package com.venturo.venturolistitem.ui.screens.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venturo.venturolistitem.ui.theme.Primary400
import com.venturo.venturolistitem.ui.theme.White

@Composable
fun MenuChip(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    imageId: Int,
    title: String = "Category",
    onClick: () -> Unit = {},
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) Black else Primary400
        ),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(color = White, blendMode = BlendMode.SrcIn),
                painter = painterResource(id = imageId),
                contentDescription = title,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }
    }
}

@Preview
@Composable
fun MenuChipPreview() {
    val imageId = android.R.drawable.ic_dialog_info
    MenuChip(imageId = imageId, isActive = true)
}
