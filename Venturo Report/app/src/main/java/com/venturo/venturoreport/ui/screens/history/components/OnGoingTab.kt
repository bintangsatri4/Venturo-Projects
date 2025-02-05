package com.venturo.venturoreport.ui.screens.history.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.venturo.venturoreport.EmptyState
import com.venturo.venturoreport.UiState
import com.venturo.venturoreport.models.Order
import com.venturo.venturoreport.ui.DetailOrderCard


@Composable
fun OnGoingTab(
    modifier: Modifier = Modifier,
    orderState: UiState<List<Order>> = UiState.Loading,
    onOrderTab: (Int) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        when (orderState) {
            is UiState.Error -> {
                item {
                    EmptyState(title = "Terjadi kesalahan pada server")
                }
            }
            is UiState.Empty -> {
                item {
                    EmptyState(title = "Tidak ada pesanan")
                }
            }
            is UiState.Loading -> {
                item {
                    repeat(4) { index ->
                        DetailOrderCard(isLoading = true)
                        if (index < 3) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
            is UiState.Success -> {
                val data = orderState.data
                itemsIndexed(data) { index, order ->
                    DetailOrderCard(
                        imageUrl = order.menu.first().photo,
                        status = order.status,
                        menuName = order.menu.first().name,
                        price = order.totalBayar.toInt(),
                        quantity = order.menu.size,
                        date = order.date,
                        withAction = false,
                        onClick = { onOrderTab(order.id) }
                    )
                    if (index < 3) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
            else -> {}
        }
    }
}
