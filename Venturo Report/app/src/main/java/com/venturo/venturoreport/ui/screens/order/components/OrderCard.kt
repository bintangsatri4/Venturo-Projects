package com.venturo.venturoreport.ui.screens.order.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venturo.venturoreport.MainNetworkImage
import com.venturo.venturoreport.ui.theme.LightGray300
import com.venturo.venturoreport.ui.theme.Primary400
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.placeholder.PlaceholderHighlight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCard(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    menuName: String? = "~",
    price: Int = 0,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .placeholder(
                visible = isLoading,
                highlight = PlaceholderHighlight.shimmer(highlightColor = Color.LightGray),
                color = Color.LightGray,
                shape = RoundedCornerShape(16.dp),
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LightGray300),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // image
            MainNetworkImage(
                modifier = Modifier.size(75.dp),
                imageUrl = imageUrl ?: "https://javacode.landa.id/img/menu/gambar_62660e47317ea.png"
            )
            Spacer(modifier = Modifier.width(12.dp))
            // order detail
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = menuName!!,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                    )
                )
                Text(
                    text = "Rp. $price",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = Primary400,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
            // action
            Text(
                modifier = Modifier.width(60.dp),
                text = "1",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}