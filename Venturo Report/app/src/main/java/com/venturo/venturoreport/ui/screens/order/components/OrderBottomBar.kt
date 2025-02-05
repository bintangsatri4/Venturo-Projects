package com.venturo.venturoreport.ui.screens.order.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.venturo.venturoreport.R
import com.venturo.venturoreport.models.Order
import com.venturo.venturoreport.UiState
import com.venturo.venturoreport.ui.screens.report.components.ReportInfo
import com.venturo.venturoreport.ui.theme.LightGray100
import com.venturo.venturoreport.ui.theme.LightGray300

@Composable
fun OrderBottomBar(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    orderState: UiState<Order> = UiState.Loading,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(color = LightGray300)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            ReportInfo(
                modifier = Modifier.placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.LightGray),
                    color = Color.LightGray,
                    shape = RoundedCornerShape(16.dp),
                ),
                title = "Total Pesanan (${if (orderState is UiState.Success) orderState.data.menu.size else 0} Menu) :",
                content = if (orderState is UiState.Success) "Rp. ${orderState.data.totalBayar}" else "Rp. 0",
                onClick = null,
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .height(1.dp)
                    .background(color = LightGray100)
            )
            ReportInfo(
                modifier = Modifier.placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.LightGray),
                    color = Color.LightGray,
                    shape = RoundedCornerShape(16.dp),
                ),
                leadingIcon = R.drawable.local_activity_24px,
                title = "Voucher",
                content = "Rp.0",
                onClick = {},
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .height(1.dp)
                    .background(color = LightGray100)
            )
            ReportInfo(
                modifier = Modifier.placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.LightGray),
                    color = Color.LightGray,
                    shape = RoundedCornerShape(16.dp),
                ),
                leadingIcon = R.drawable.paid_24px,
                title = "Pembayaran",
                content = "PayLater",
                contentStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                )
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .height(1.dp)
                    .background(color = LightGray100)
            )
            ReportInfo(
                modifier = Modifier.placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.LightGray),
                    color = Color.LightGray,
                    shape = RoundedCornerShape(16.dp),
                ),
                leadingIcon = R.drawable.paid_24px,
                title = "Total Pembayaran",
                content = if (orderState is UiState.Success) "Rp. ${orderState.data.totalBayar}" else "Rp. 0",
                contentStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                )
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .height(1.dp)
                    .background(color = LightGray100)
            )
            Spacer(modifier = Modifier.height(12.dp))
            OrderStepper(
                isLoading = isLoading,
                label = "Pesanan kamu sedang disiapkan",
                step = if (orderState !is UiState.Success) 0 else orderState.data.status,
                steps = listOf(
                    StepperItem("Pesanan Diterima"),
                    StepperItem("Silahkan Diambil"),
                    StepperItem("Pesanan Selesai")
                )
            )
        }
    }
}