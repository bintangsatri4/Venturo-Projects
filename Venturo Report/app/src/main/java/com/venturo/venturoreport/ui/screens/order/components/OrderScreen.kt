package com.venturo.venturoreport.ui.screens.order.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.venturo.venturoreport.EmptyState
import com.venturo.venturoreport.R
import com.venturo.venturoreport.UiState
import com.venturo.venturoreport.models.Menu
import com.venturo.venturoreport.models.Order
import com.venturo.venturoreport.ui.components.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrderScreen(
    navController: NavController,
    viewModel: OrderViewModel = koinViewModel(),
    orderId: Int,
    modifier: Modifier = Modifier
) {
    val orderState by viewModel.orderState.collectAsState()
    val foods by viewModel.foodOrders.collectAsState()
    val drinks by viewModel.drinkOrders.collectAsState()

    // set orderId to viewmodel state
    LaunchedEffect(orderId) {
        viewModel.getOrderDetail(orderId)
    }

    Scaffold(
        topBar = {
            OrderAppBar(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        },
        bottomBar = {
            OrderBottomBar(
                isLoading = orderState !is UiState.Success,
                orderState = orderState,
            )
        }
    ) { paddingValues ->
        OrderContent(
            modifier = Modifier.padding(paddingValues),
            orderState = orderState,
            foods = foods,
            drinks = drinks,
        )
    }
}

@Composable
fun OrderContent(
    modifier: Modifier = Modifier,
    orderState: UiState<Order> = UiState.Loading,
    foods: List<Menu> = listOf(),
    drinks: List<Menu> = listOf()
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 24.dp),
    ) {
        when (orderState) {
            is UiState.Error -> {
                item {
                    EmptyState(
                        modifier = Modifier.fillMaxSize(),
                        title = "Terjadi kesalahan pada server"
                    )
                }
            }
            is UiState.Empty -> {
                item {
                    EmptyState(
                        title = "Tidak ada pesanan"
                    )
                }
            }
            is UiState.Loading -> {
                item {
                    repeat(2) { index ->
                        OrderCard(isLoading = true)
                        if (index < 1) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
            is UiState.Success -> {
                // both drink and food is empty
                if (foods.isEmpty() && drinks.isEmpty()) {
                    item {
                        EmptyState(
                            title = "Tidak ada pesanan"
                        )
                    }
                }
                // food
                if (foods.isNotEmpty()) {
                    // food section
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        SectionHeader(
                            imageId = R.drawable.restaurant_24px,
                            title = "Makanan"
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                    itemsIndexed(foods) { index, food ->
                        OrderCard(
                            imageUrl = food.photo,
                            menuName = food.name,
                            price = food.price.toInt(),
                            onClick = {}
                        )
                        if (index < (foods.size - 1)) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
                // drinking
                if (drinks.isNotEmpty()) {
                    // drink section
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        SectionHeader(
                            imageId = R.drawable.local_cafe_24px,
                            title = "Minuman"
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                    itemsIndexed(drinks) { index, drink ->
                        OrderCard(
                            imageUrl = drink.photo,
                            menuName = drink.name,
                            price = drink.price.toInt(),
                            onClick = {}
                        )
                        if (index < (drinks.size - 1)) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}