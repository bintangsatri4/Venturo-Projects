package com.venturo.venturolistitem.ui.screens.list

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturo.venturolistitem.data.repositories.MenuRepository
import com.venturo.venturolistitem.ui.state.UiState
import com.venturo.venturolistitem.Menu
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class ListViewModel(
    private val context: Context,
    private val menuRepository: MenuRepository
) : ViewModel() {

    // menu related variables
    private val _menuState: MutableStateFlow<UiState<List<Menu>>> =
        MutableStateFlow(UiState.Loading)
    val menuState: StateFlow<UiState<List<Menu>>> get() = _menuState

    private val _selectedMenu: MutableStateFlow<List<Menu>> = MutableStateFlow(listOf())
    val selectedMenu: StateFlow<List<Menu>> get() = _selectedMenu

    // category
    private val _categories: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf("All", "Food", "Drink"))
    val categories: StateFlow<List<String>> get() = _categories

    // defaulted to all / first item in _categories
    private val _selectedCategory: MutableStateFlow<String> = MutableStateFlow(_categories.value[0])
    val selectedCategory: StateFlow<String> get() = _selectedCategory

    // search
    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    // load more loading indicator
    private val _isLoadMore: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoadMore: StateFlow<Boolean> get() = _isLoadMore

    private val _canLoadMore: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val canLoadMore: StateFlow<Boolean> get() = _canLoadMore

    // page and refreshing indicator
    private val _page: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    init {
        debounceSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun debounceSearchQuery() {
        viewModelScope.launch {
            _query
                .debounce(400L)
                .distinctUntilChanged()
                .collectLatest {
                    getMenuList()
                }
        }
    }

    fun getMenuList(
        isRefresh: Boolean = false,
        isLoadMore: Boolean = false,
        withRefreshIndicator: Boolean = true
    ) {
        // if refresh, reset page to 0 and set refresh indicator to true
        if (isRefresh) {
            if (withRefreshIndicator) {
                _isRefreshing.value = true
            }
            _page.value = 0
        }
        if (withRefreshIndicator) {
            _menuState.value = UiState.Loading
        }
        viewModelScope.launch {
            // simulate loading api call
            delay(500)
            // fetch data based on query (search by name), category and page
            menuRepository.getMenuList(
                query = _query.value,
                category = _selectedCategory.value,
                page = _page.value,
            ).collect { response ->
                _menuState.value = when {
                    response?.error != null -> {
                        UiState.Error(response.error)
                    }
                    // data is null or empty
                    response?.data == null || response.data.isEmpty() -> {
                        UiState.Empty
                    }
                    else -> {
                        // add more page for next data loading
                        _page.value += 1
                        // detect if load more is available or not
                        _canLoadMore.value = !(response.pagination?.isLast ?: false)
                        /**
                         * if load more data
                         * add fetched data to current menu data
                         * else
                         * assign fetched data to current menu data
                         */
                        if (isLoadMore && (_menuState.value is UiState.Success)) {
                            val currentList = (_menuState.value as UiState.Success).data.toMutableList()
                            currentList.addAll(response.data)
                            _isLoadMore.value = true
                            UiState.Success(currentList)
                        } else {
                            UiState.Success(response.data)
                        }
                    }
                }
                // set refresh indicator to false
                _isRefreshing.value = false
            }
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        _page.value = 0
        getMenuList()
    }

    fun updateSearchQuery(newQuery: String) {
        _query.value = newQuery
        _page.value = 0
    }

    fun deleteMenu(menu: Menu) {
        menu.idMenu?.let {
            viewModelScope.launch {
                menuRepository.deleteOne(menu.idMenu).collect { response ->
                    if (response) {
                        Toast.makeText(context, "Menu Berhasil dihapus", Toast.LENGTH_SHORT).show()
                        // delete menu from selected menu
                        selectMenu(menu)
                        getMenuList(isRefresh = true, withRefreshIndicator = false)
                    } else {
                        Toast.makeText(context, "Gagal menghapus menu", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun selectMenu(menu: Menu) {
        _selectedMenu.value = _selectedMenu.value.toMutableList().apply {
            if (contains(menu)) {
                remove(menu)
            } else {
                add(menu)
            }
        }
    }
}