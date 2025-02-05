package com.venturo.venturoreport.ui.screens.order.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.venturo.venturoreport.ui.theme.LightGray400
import com.venturo.venturoreport.ui.theme.Primary400
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.placeholder.PlaceholderHighlight

data class StepperItem(
    val title: String
)

@Composable
fun OrderStepper(
    modifier: Modifier = Modifier,
    label: String? = null,
    step: Int = 0,
    stepCount: Int = 3,
    isLoading: Boolean = false,
    steps: List<StepperItem>
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    Column {
        if (label != null) {
            Text(
                modifier = Modifier.placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.LightGray),
                    color = Color.LightGray,
                    shape = RoundedCornerShape(16.dp),
                ),
                text = label,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row(
            verticalAlignment = Alignment.Top,
            modifier = modifier.fillMaxWidth(),
        ) {
            for ((i, item) in steps.withIndex()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Wrapper
                    Box(
                        modifier = Modifier.size(22.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Circle
                        Box(
                            modifier = Modifier
                                .shadow(elevation = 3.dp, shape = RoundedCornerShape(24.dp))
                                .clip(shape = RoundedCornerShape(24.dp))
                                .size(if (i > step) 10.dp else 22.dp)
                                .zIndex(2f)
                                .background(
                                    color = if (i <= step) Primary400 else LightGray400
                                )
                                .placeholder(
                                    visible = isLoading,
                                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.LightGray),
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(16.dp),
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (i <= step) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier
                            .widthIn(max = (screenWidthDp.dp / (steps.size * 2)))
                            .placeholder(
                                visible = isLoading,
                                highlight = PlaceholderHighlight.shimmer(highlightColor = Color.LightGray),
                                color = Color.LightGray,
                                shape = RoundedCornerShape(16.dp),
                            ),
                        text = item.title,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    )
                }
                if (i < stepCount && i != steps.size - 1) {
                    // Wrapper
                    Box(
                        modifier = Modifier
                            .height(22.dp)
                            .weight(1f),
                        contentAlignment = Alignment.Center,
                    ) {
                        // Line
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3.dp)
                                .padding(horizontal = 4.dp)
                                .zIndex(1f)
                                .background(
                                    color = if (i < step) Primary400 else LightGray400
                                )
                                .placeholder(
                                    visible = isLoading,
                                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.LightGray),
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(16.dp),
                                ),
                        )
                    }
                }
            }
        }
    }
}
