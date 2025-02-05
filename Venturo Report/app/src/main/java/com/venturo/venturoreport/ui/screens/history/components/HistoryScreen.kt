package com.venturo.venturoreport.ui.screens.history.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.venturo.venturoreport.models.Order
import com.venturo.venturoreport.UiState
import com.venturo.venturoreport.ui.commons.DropdownItem
import com.venturo.venturoreport.ui.components.HistoryBottomBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    navController: NavController = rememberNavController(),
    viewModel: HistoryViewModel = koinViewModel(),
) {
    var activeTabIndex by remember { mutableStateOf(0) }
    val selectedStatus by viewModel.statusFilter.collectAsState()
    val onGoingOrderState by viewModel.onGoingOrderState.collectAsState()
    val orderState by viewModel.ordersState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (activeTabIndex == 1) {
                HistoryBottomBar(
                    price = (orderState as? UiState.Success)?.data?.sumOf { it.totalBayar }?.toInt() ?: 0
                )
            }
        }
    ) { paddingValues ->
        HistoryContent(
            modifier = Modifier.padding(paddingValues),
            activeTabIndex = activeTabIndex,
            statusOptions = viewModel.statusOptions,
            selectedStatus = selectedStatus,
            onGoingOrderState = onGoingOrderState,
            orderState = orderState,
            onStatusSelect = { item -> viewModel.onStatusSelect(item) },
            onTabChanged = { index -> activeTabIndex = index },
            onOrderTab = { },
            onDatePicked = { date -> viewModel.onDatePicked(date) }
        )
    }
}

@Composable
fun HistoryContent(
    modifier: Modifier = Modifier,
    activeTabIndex: Int = 0,
    statusOptions: List<DropdownItem>,
    selectedStatus: DropdownItem? = null,
    orderState: UiState<List<Order>> = UiState.Loading,
    onGoingOrderState: UiState<List<Order>> = UiState.Loading,
    onStatusSelect: (DropdownItem) -> Unit,
    onDatePicked: (Pair<Long, Long>) -> Unit,
    onTabChanged: (Int) -> Unit,
    onOrderTab: (Int) -> Unit,
) {
    Column(modifier = modifier) {
        HistoryTabRow(
            activeTabIndex = activeTabIndex,
            onTabChanged = onTabChanged
        )
        when (activeTabIndex) {
            0 -> OnGoingTab(
                orderState = onGoingOrderState,
                onOrderTab = onOrderTab,
            )
            1 -> HistoryTab(
                orderState = orderState,
                selectedStatus = selectedStatus,
                statusOptions = statusOptions,
                onStatusSelect = onStatusSelect,
                onDatePicked = onDatePicked,
                onOrderTab = onOrderTab,
            )
            else -> OnGoingTab()
        }
    }
}

@Composable
fun HistoryTabRow(
    activeTabIndex: Int,
    onTabChanged: (Int) -> Unit
) {
    TabRow(selectedTabIndex = activeTabIndex) {
        Tab(
            selected = activeTabIndex == 0,
            onClick = { onTabChanged(0) },
            text = { Text("On Going") }
        )
        Tab(
            selected = activeTabIndex == 1,
            onClick = { onTabChanged(1) },
            text = { Text("History") }
        )
    }
}