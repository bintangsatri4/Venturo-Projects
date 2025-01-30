package com.venturo.venturolistitem.data.repositories

import com.venturo.venturolistitem.DummyMenuList
import com.venturo.venturolistitem.Menu
import com.venturo.venturolistitem.date.remote.models.Pagination
import com.venturo.venturolistitem.date.remote.models.menu.MenuListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class MenuRepository {
    // simulate api data
    private val menuData = DummyMenuList.list

    /**
     * Fetch menu list from api
     *
     * fetch menu with pagination
     */
    suspend fun getMenuList(
        query: String = "",
        category: String = "",
        page: Int = 0,
        perPage: Int = 5
    ): Flow<MenuListResponse?> = withContext(Dispatchers.IO) {
        try {
            val data = menuData.filter {
                val nameMatches = if (query.isBlank()) true else it.name?.contains(query, ignoreCase = true) == true
                val categoryMatches = if (category.equals("all", ignoreCase = true)) true else it.category?.equals(category, ignoreCase = true) == true
                nameMatches && categoryMatches
            }

            val offset = page * perPage
            val startIndex = offset.coerceIn(0, data.size)
            val endIndex = (offset + perPage).coerceIn(startIndex, data.size)
            val paginatedData = data.subList(startIndex, endIndex)
            val pagination = Pagination(
                totalPage = data.size,
                limit = perPage,
                isLast = endIndex == data.size
            )
            val response = MenuListResponse(
                data = paginatedData,
                statusCode = 200,
                pagination = pagination
            )
            flowOf(response)
        } catch (e: Exception) {
            val errorResponse = MenuListResponse(
                statusCode = 500,
                error = e.message.toString()
            )
            flowOf(errorResponse)
        }
    }

    // Function to delete a menu item
    fun deleteMenu(menu: Menu): Flow<Unit> {
        return flow {
            menuData.removeIf { it.idMenu == menu.idMenu }
            emit(Unit)
        }
    }

    // delete one menu
    fun deleteOne(id: Int): Flow<Boolean> {
        return flowOf(menuData.removeIf { it.idMenu == id })
    }
}