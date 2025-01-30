package com.venturo.venturolistitem

import org.koin.dsl.module // Ensure this import is correct.
import org.koin.core.module.Module // Import the Module class.
import com.venturo.venturolistitem.data.repositories.MenuRepository

val appModules: Module = module {
    single { MenuRepository() }
}
