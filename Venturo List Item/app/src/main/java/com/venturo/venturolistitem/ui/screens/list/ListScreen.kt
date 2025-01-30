package com.venturo.venturolistitem.ui.screens.list

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.venturo.venturolistitem.Menu
import com.venturo.venturolistitem.R
import com.venturo.venturolistitem.ui.screens.list.components.MenuCard
import com.venturo.venturolistitem.ui.screens.list.components.MenuChip
import com.venturo.venturolistitem.ui.screens.list.components.PromoCard
import com.venturo.venturolistitem.ui.screens.list.components.SearchBarr
import com.venturo.venturolistitem.ui.screens.list.components.SectionHeader
import com.venturo.venturolistitem.ui.state.UiState
import com.venturo.venturolistitem.ui.theme.Primary400
import com.venturo.venturolistitem.ui.theme.White
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = koinViewModel(),
) {
    val menuState by viewModel.menuState.collectAsState()
    val query by viewModel.query.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val selectedMenu by viewModel.selectedMenu.collectAsState()

    Scaffold(
        containerColor = White,
        topBar = {
            SearchBarr(
                query = query,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                onSearch = { viewModel.getMenuList() },
                active = true,
                onValueChange = { viewModel.updateSearchQuery(it) },
                onActiveChange = {}
            )
        }
    ) { padding ->
        ListContent(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            menuState = menuState,
            selectedMenu = selectedMenu,
            categories = categories,
            selectedCategory = selectedCategory,
            onCategoryClick = { category ->
                viewModel.selectCategory(category)
            },
            onMenuClick = { menu ->
                viewModel.selectMenu(menu)
            },
            onMenuDelete = { menu ->
                viewModel.deleteMenu(menu)
            },
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    menuState: UiState<List<Menu>> = UiState.Loading,
    selectedMenu: List<Menu> = listOf(),
    categories: List<String>,
    selectedCategory: String? = null,
    onCategoryClick: (String) -> Unit = {},
    onMenuClick: (Menu) -> Unit = {},
    onMenuDelete: (Menu) -> Unit = {},
    isRefreshing: Boolean = false,
    isLoadMore: Boolean = false,
    canLoadMore: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
) {
    PullToRefreshBox(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
        ) {
            item {
                Spacer(modifier = Modifier.height(22.dp))
                // promo section
                SectionHeader(
                    isHighlight = true,
                    imageId = R.drawable.local_activity_24px,
                    title = stringResource(R.string.available_promo),
                    modifier = Modifier.padding(bottom = 22.dp)
                )
                Row(
                    modifier = Modifier
                        .horizontalScroll(state = rememberScrollState())
                        .padding(horizontal = 24.dp)
                ) {
                    repeat(3) { index ->
                        PromoCard(
                            imageUrl = "https://javacode.landa.id/img/promo/gambar_62661b52223ff.png",
                            title = "Diskon",
                            discount = "${(index + 1) * 10}%",
                            description = "Mengisi review yang ditentukan",
                            modifier = Modifier.padding(end = 26.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(22.dp))
            }
            // category section
            item {
                Row(
                    modifier = Modifier
                        .horizontalScroll(state = rememberScrollState())
                        .padding(horizontal = 24.dp)
                ) {
                    repeat(categories.size) { index ->
                        MenuChip(
                            imageId = R.drawable.menu_book_24px,
                            isActive = categories[index].equals(selectedCategory, ignoreCase = true),
                            title = categories[index],
                            modifier = Modifier.padding(end = 12.dp),
                            onClick = { onCategoryClick(categories[index]) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(22.dp))
            }
            item {
                SectionHeader(
                    imageId = R.drawable.restaurant_24px,
                    title = stringResource(R.string.food)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            when (menuState) {
                is UiState.Empty -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(bottom = 16.dp),
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Primary400,
                            )
                            Text(text = "Tidak ada menu yang ditemukan")
                        }
                    }
                }
                is UiState.Error -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(bottom = 16.dp),
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Primary400,
                            )
                            Text(text = "Terjadi kesalahan")
                        }
                    }
                }
                is UiState.Loading -> {
                    item {
                        repeat(4) {
                            MenuCard(
                                isLoading = true,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    }
                }
                is UiState.Success -> {
                    val data = menuState.data
                    items(data) { menu ->
                        MenuCard(
                            imageUrl = menu.photo,
                            title = menu.name,
                            price = menu.price,
                            modifier = Modifier.padding(bottom = 16.dp),
                            isSelected = selectedMenu.contains(menu),
                            onClick = { onMenuClick(menu) },
                            onDelete = { onMenuDelete(menu) }
                        )
                    }
                }
                else -> {}
            }
            item {
                if (isLoadMore && canLoadMore) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Primary400)
                    }
                }
            }
            item {
                LaunchedEffect(key1 = listState) {
                    snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                        .collect { lastVisibleItemIndex ->
                            if (lastVisibleItemIndex == listState.layoutInfo.totalItemsCount - 1 && canLoadMore) {
                                onLoadMore()
                            }
                        }
                }
            }
        }
    }
}