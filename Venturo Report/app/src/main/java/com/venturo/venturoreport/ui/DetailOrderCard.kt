package com.venturo.venturoreport.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.placeholder
import com.venturo.venturoreport.MainNetworkImage
import com.venturo.venturoreport.PrimaryButton
import com.venturo.venturoreport.ui.theme.Cyan400
import com.venturo.venturoreport.ui.theme.Danger400
import com.venturo.venturoreport.ui.theme.LightGray300
import com.venturo.venturoreport.ui.theme.LightGray400
import com.venturo.venturoreport.ui.theme.Success400
import com.venturo.venturoreport.ui.theme.Warning400
import com.venturo.venturoreport.DateFormatter
import com.venturo.venturoreport.RichText
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.placeholder.PlaceholderHighlight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailOrderCard(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    status: Int = 0,
    date: String? = null,
    menuName: String? = "~",
    price: Int = 0,
    quantity: Int = 0,
    withAction: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    fun status(): String {
        return when (status) {
            0 -> "Menunggu"
            1, 2 -> "Silahkan Diambil"
            3 -> "Selesai"
            4 -> "Dibatalkan"
            else -> "Menunggu"
        }
    }

    fun statusIcon(): Pair<ImageVector, Color> {
        return when (status) {
            0, 1, 2 -> Pair(Icons.Default.ExitToApp, Warning400)
            3 -> Pair(Icons.Default.Check, Success400)
            4 -> Pair(Icons.Default.Close, Danger400)
            else -> Pair(Icons.Default.ExitToApp, Warning400)
        }
    }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isLoading) {
                    Modifier.placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = Color.LightGray),
                        color = Color.LightGray,
                        shape = RoundedCornerShape(16.dp))
                } else Modifier
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LightGray300),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MainNetworkImage(
                modifier = Modifier.size(124.dp),
                imageUrl = imageUrl
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.fillMaxSize()) {
                // date
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = statusIcon().first,
                        contentDescription = "order status",
                        tint = statusIcon().second
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = status(),
                        style = TextStyle(
                            color = statusIcon().second,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = DateFormatter.formatDateString(
                            dateString = date,
                            outputFormat = "dd MMM yyyy"
                        ),
                        style = TextStyle(
                            color = LightGray400,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // content
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = menuName!!,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    RichText(
                        text = "Rp. $price",
                        highlightedText = "($quantity Menu)",
                        textStyle = SpanStyle(
                            fontSize = 14.sp,
                            color = Cyan400,
                            fontWeight = FontWeight.Medium
                        ),
                        highlightedTextStyle = SpanStyle(
                            fontSize = 12.sp,
                            color = LightGray400,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // action
                if (withAction) {
                    Row {
                        PrimaryButton(
                            modifier = Modifier.weight(1f),
                            title = "Beri Penilaian",
                            filled = false
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        PrimaryButton(
                            title = "Pesan Lagi",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

