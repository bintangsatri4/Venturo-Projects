package com.venturo.venturoreport.ui.screens.history.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturo.venturoreport.models.Order
import com.venturo.venturoreport.UiState
import com.venturo.venturoreport.repository.OrderRepository
import com.venturo.venturoreport.ui.commons.DropdownItem
import com.venturo.venturoreport.DateFormatter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val orderRepository: OrderRepository,
) : ViewModel() {

    // on going order data
    private val _onGoingOrderState: MutableStateFlow<UiState<List<Order>>> = MutableStateFlow(UiState.Loading)
    val onGoingOrderState: StateFlow<UiState<List<Order>>> get() = _onGoingOrderState

    // order data
    private val _ordersState: MutableStateFlow<UiState<List<Order>>> = MutableStateFlow(UiState.Loading)
    val ordersState: StateFlow<UiState<List<Order>>> get() = _ordersState

    // filter by status option
    val statusOptions = listOf(
        DropdownItem(title = "Semua Status", value = "0"),
        DropdownItem(title = "Selesai", value = "3"),
        DropdownItem(title = "Dibatalkan", value = "4")
    )

    // filter by var
    private val _statusFilter: MutableStateFlow<DropdownItem?> = MutableStateFlow(null)
    val statusFilter: StateFlow<DropdownItem?> get() = _statusFilter

    private val _dateFilter: MutableStateFlow<Pair<Long, Long>?> = MutableStateFlow(null)

    init {
        getOrders()
        getOnGoingOrder()
    }

    private fun getOrders() {
        // set loading indicator
        _ordersState.value = UiState.Loading

        // if selected filter is null / filter is Semua/0 skip filtering and return null
        val filterStatus = if (_statusFilter.value == null || _statusFilter.value!!.value == "0") null else listOf(_statusFilter.value!!.value.toInt())

        /**
         * if selected date filter is null skip filtering and return null
         * else convert time in millis to yyyy-MM-dd date format (2024-01-30)
         */
        val filterDate = if (_dateFilter.value == null) null else DateFormatter.millisecondsToDate(
            milliseconds = _dateFilter.value!!.first,
            dateFormat = "yyyy-MM-dd"
        )

        viewModelScope.launch {
            // simulate api calls
            delay(500)
            // fetch api
            orderRepository.getOrder(
                status = filterStatus,
                date = filterDate,
            ).collect { response ->
                _ordersState.value = when {
                    // error
                    response?.error != null -> {
                        UiState.Error(response.error)
                    }
                    // data is null or empty
                    response?.data == null || response.data.isEmpty() -> {
                        UiState.Empty
                    }
                    else -> {
                        UiState.Success(response.data)
                    }
                }
            }
        }
    }

    // get order where order status 0/1/2
    private fun getOnGoingOrder() {
        viewModelScope.launch {
            delay(500)
            orderRepository.getOnGoingOrder().collect { response ->
                _onGoingOrderState.value = when {
                    response?.error != null -> {
                        UiState.Error(response.error)
                    }
                    response?.data == null || response.data.isEmpty() -> {
                        UiState.Empty
                    }
                    else -> {
                        UiState.Success(response.data)
                    }
                }
            }
        }
    }

    fun onStatusSelect(item: DropdownItem) {
        _statusFilter.value = item
        getOrders()
    }

    fun onDatePicked(date: Pair<Long, Long>) {
        _dateFilter.value = date
        getOrders()
    }
}