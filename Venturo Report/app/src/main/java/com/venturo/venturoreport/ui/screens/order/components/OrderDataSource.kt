package com.venturo.venturoreport.ui.screens.order.components

import com.venturo.venturoreport.models.Order
import com.venturo.venturoreport.repository.DummyOrderList

class OrderDataSource {
    fun getOrderDetail(orderId: Int): Order? {
        // Fetch order details from DummyOrderList
        return DummyOrderList.orders.find { it.id == orderId }
    }
}