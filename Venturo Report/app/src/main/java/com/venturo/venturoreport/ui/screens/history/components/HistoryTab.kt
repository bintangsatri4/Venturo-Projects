package com.venturo.venturoreport.ui.screens.history.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.venturo.venturoreport.EmptyState
import com.venturo.venturoreport.UiState
import com.venturo.venturoreport.models.Order
import com.venturo.venturoreport.ui.DetailOrderCard
import com.venturo.venturoreport.ui.commons.CustomDropdownMenu
import com.venturo.venturoreport.ui.commons.DropdownItem
import com.venturo.venturoreport.ui.components.RoundedDatePicker

@Composable
fun HistoryTab(
    modifier: Modifier = Modifier,
    selectedStatus: DropdownItem? = null,
    statusOptions: List<DropdownItem>,
    orderState: UiState<List<Order>> = UiState.Loading,
    onStatusSelect: (DropdownItem) -> Unit,
    onDatePicked: (Pair<Long, Long>) -> Unit,
    onOrderTab: (Int) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        // Filtering Action
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomDropdownMenu(
                    modifier = Modifier.weight(1f),
                    options = statusOptions,
                    selectedOption = selectedStatus,
                    onSelect = onStatusSelect
                )
                Spacer(modifier = Modifier.width(16.dp))
                RoundedDatePicker(
                    modifier = Modifier.weight(1f),
                    onDatePicked = onDatePicked
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
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
                        withAction = true,
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

