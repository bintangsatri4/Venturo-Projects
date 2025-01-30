package com.venturo.venturolistitem.ui.screens.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.venturo.venturolistitem.MainNetworkImage
import com.venturo.venturolistitem.ui.theme.Primary400
import com.venturo.venturolistitem.ui.theme.Primary50
import com.venturo.venturolistitem.ui.components.view.SwipeBackground

@Composable
fun MenuCard(
    modifier: Modifier = Modifier,
    imageUrl: String? = "",
    title: String? = "",
    price: Int? = 0,
    isLoading: Boolean = false,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd, SwipeToDismissBoxValue.EndToStart -> {
                    onDelete()
                    false
                }
                SwipeToDismissBoxValue.Settled -> false
            }
        },
        positionalThreshold = { it * .25f }
    )

    SwipeToDismissBox(
        modifier = modifier.padding(horizontal = 24.dp),
        state = dismissState,
        backgroundContent = { SwipeBackground(dismissState = dismissState) }
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = if (isSelected) 2.dp else 0.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) Primary400 else Color.Transparent
                )
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                    color = Color.LightGray,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable { onClick() }
                .background(if (isSelected) Color.Gray else Color.White),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Primary50)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                MainNetworkImage(
                    imageUrl = imageUrl,
                    contentDescription = title,
                    modifier = Modifier.size(75.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title ?: "",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Rp. $price",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary400
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MenuCardPreview() {
    MenuCard(
        imageUrl = "https://via.placeholder.com/150",
        title = "Menu Title",
        price = 10000,
        isLoading = false,
        isSelected = true,
        onClick = {
            // Handle click
        },
        onDelete = {
            // Handle delete
        }

    )
}