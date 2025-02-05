package com.venturo.venturoreport.ui.screens.report.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venturo.venturoreport.ui.theme.Danger400
import com.venturo.venturoreport.ui.theme.Primary400

@Composable
fun ReportInfo(
    modifier: Modifier = Modifier,
    leadingIcon: Int? = null,
    title: String? = "Information",
    content: String? = "Value",
    contentStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Primary400,
    ),
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                Image(
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp),
                    painter = painterResource(id = leadingIcon),
                    contentDescription = "leading icon",
                    contentScale = ContentScale.Fit,
                )
            }
            Text(
                text = title!!,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                )
            )
        }
        if (onClick != null) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = content!!,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Danger400,
                        )
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }
        } else {
            Text(
                text = content!!,
                style = contentStyle,
            )
        }
    }
}

