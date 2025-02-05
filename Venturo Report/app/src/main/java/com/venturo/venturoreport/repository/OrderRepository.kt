package com.venturo.venturoreport.repository

import com.venturo.venturoreport.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class OrderRepository {

    // Simulate API data
    private val menuData = DummyOrderList.orders

    suspend fun getOrder(status: List<Int>?, date: String? = null): Flow<ListOrderResponse?> =
        withContext(Dispatchers.IO) {
            try {
                /**
                 * Order status
                 * 0 = menunggu
                 * 1 = dipersiapkan
                 * 2 = diambil
                 * 3 = completed
                 * 4 = canceled
                 */
                // if filter is null filter by status 3 and 4
                val filterState = status ?: listOf(3, 4)
                val data = ListOrderResponse(
                    statusCode = 200,
                    data = menuData.filter {
                        (it.status in filterState) && (date == null || it.date == date)
                    }
                )
                flowOf(data)
            } catch (e: Exception) {
                val errorResponse = ListOrderResponse(
                    statusCode = 500,
                    error = e.message.toString()
                )
                flowOf(errorResponse)
            }
        }

    suspend fun getOnGoingOrder(): Flow<ListOrderResponse?> =
        withContext(Dispatchers.IO) {
            try {
                /**
                 * Order status
                 * 0 = menunggu
                 * 1 = dipersiapkan
                 * 2 = diambil
                 * 3 = completed
                 * 4 = canceled
                 */
                // filter by status 'menunggu', 'dipersiapkan', or 'diambil'
                val data = ListOrderResponse(
                    statusCode = 200,
                    data = menuData.filter { it.status < 3 }
                )
                flowOf(data)
            } catch (e: Exception) {
                val errorResponse = ListOrderResponse(
                    statusCode = 500,
                    error = e.message.toString()
                )
                flowOf(errorResponse)
            }
        }

    suspend fun getOrderById(id: String): Flow<DetailOrderResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val data = DetailOrderResponse(
                    statusCode = 200,
                    data = menuData.find { it.id == id.toIntOrNull() }
                )
                flowOf(data)
            } catch (e: Exception) {
                val errorResponse = DetailOrderResponse(
                    statusCode = 500,
                    error = e.message.toString()
                )
                flowOf(errorResponse)
            }
        }
}