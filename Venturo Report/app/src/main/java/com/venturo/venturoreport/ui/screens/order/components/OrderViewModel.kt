package com.venturo.venturoreport.ui.screens.order.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturo.venturoreport.models.Menu
import com.venturo.venturoreport.models.Order
import com.venturo.venturoreport.UiState
import com.venturo.venturoreport.repository.OrderRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(
    private val orderRepository: OrderRepository,
) : ViewModel() {
    // order data
    private val _orderState: MutableStateFlow<UiState<Order>> = MutableStateFlow(UiState.Loading)
    val orderState: StateFlow<UiState<Order>> get() = _orderState

    // foods data order
    private val _foodOrders: MutableStateFlow<List<Menu>> = MutableStateFlow(listOf())
    val foodOrders: StateFlow<List<Menu>> get() = _foodOrders

    // drink data order
    private val _drinkOrders: MutableStateFlow<List<Menu>> = MutableStateFlow(listOf())
    val drinkOrders: StateFlow<List<Menu>> get() = _drinkOrders

    // get detail order by id
    fun getOrderDetail(id: Int) {
        viewModelScope.launch {
            // simulate api call
            delay(500)
            orderRepository.getOrderById(id.toString()).collect { response ->
                _orderState.value = when {
                    response?.error != null -> {
                        UiState.Error(response.error)
                    }
                    response?.data == null -> {
                        UiState.Empty
                    }
                    else -> {
                        // assign based on category
                        _foodOrders.value = response.data.menu.filter {
                            it.kategori.contains("makanan", ignoreCase = true)
                        }
                        _drinkOrders.value = response.data.menu.filter {
                            it.kategori.contains("minuman", ignoreCase = true)
                        }
                        UiState.Success(response.data)
                    }
                }
            }
        }
    }
}