package com.venturo.venturoreport.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venturo.venturoreport.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderAppBar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(
            bottomEnd = 24.dp,
            bottomStart = 24.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onBackPressed() },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back arrow",
                tint = Color.Black,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.restaurant_24px),
                    contentDescription = "dinner icon",
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Pesanan",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                )
            }
            Spacer(modifier = Modifier.size(32.dp))
        }
    }
}

@Preview
@Composable
fun OrderAppBarPreview() {
    OrderAppBar()
}