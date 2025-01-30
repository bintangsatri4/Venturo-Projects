package com.venturo.venturolistitem

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.venturo.venturolistitem.ui.screens.list.ListContent
import com.venturo.venturolistitem.ui.screens.list.ListViewModel
import com.venturo.venturolistitem.ui.theme.VenturoListItemTheme
import com.venturo.venturolistitem.data.repositories.MenuRepository

class MainActivity : ComponentActivity() {
    private val listViewModel: ListViewModel by viewModels {
        ListViewModelFactory(applicationContext, MenuRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VenturoListItemTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListScreen(
                        viewModel = listViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

class ListViewModelFactory(
    private val context: Context,
    private val menuRepository: MenuRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(context, menuRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun ListScreen(viewModel: ListViewModel, modifier: Modifier = Modifier) {
    val menuState = viewModel.menuState.collectAsState().value
    val selectedMenu = viewModel.selectedMenu.collectAsState().value
    val categories = viewModel.categories.collectAsState().value
    val selectedCategory = viewModel.selectedCategory.collectAsState().value
    val isRefreshing = viewModel.isRefreshing.collectAsState().value
    val isLoadMore = viewModel.isLoadMore.collectAsState().value
    val canLoadMore = viewModel.canLoadMore.collectAsState().value

    ListContent(
        modifier = modifier,
        menuState = menuState,
        selectedMenu = selectedMenu,
        categories = categories,
        selectedCategory = selectedCategory,
        onCategoryClick = { viewModel.selectCategory(it) },
        onMenuClick = { viewModel.selectMenu(it) },
        onMenuDelete = { viewModel.deleteMenu(it) },
        isRefreshing = isRefreshing,
        isLoadMore = isLoadMore,
        canLoadMore = canLoadMore,
    )
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreviewItem() {
    val context = LocalContext.current
    val menuRepository = MenuRepository() // Provide a real or mock instance
    val listViewModel = ListViewModel(context, menuRepository)

    VenturoListItemTheme {
        ListScreen(viewModel = listViewModel)
    }
}