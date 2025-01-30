package com.venturo.venturoreport.ui.screens.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venturo.venturoreport.ui.theme.Primary400


@Composable
fun HistoryTabRow(
    modifier: Modifier = Modifier,
    activeTabIndex: Int = 0,
    onTabChanged: (Int) -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(
            bottomStart = 28.dp,
            bottomEnd = 28.dp
        )
    ) {
        TabRow(
            modifier = Modifier.padding(
                vertical = 16.dp,
                horizontal = 24.dp
            ),
            selectedTabIndex = activeTabIndex,
            containerColor = Color.Transparent,
            divider = {},
            indicator = { tabIndicator ->
                val tab = tabIndicator[activeTabIndex]
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tab)
                        .height(2.dp)
                        .padding(horizontal = tab.width / 3)
                        .background(color = Primary400)
                )
            }
        ) {
            Tab(
                selected = activeTabIndex == 0,
                onClick = { onTabChanged(0) }
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = "Sedang Berjalan",
                    style = TextStyle(
                        color = if (activeTabIndex == 0) Primary400 else Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Tab(
                selected = activeTabIndex == 1,
                onClick = { onTabChanged(1) }
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = "Riwayat",
                    style = TextStyle(
                        color = if (activeTabIndex == 1) Primary400 else Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun HistoryTabRowPreview() {
    HistoryTabRow()
}
