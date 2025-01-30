package com.venturo.venturolistitem.ui.screens.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.venturo.venturolistitem.data.repositories.MenuRepository
import com.venturo.venturolistitem.ui.theme.VenturoListItemTheme

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