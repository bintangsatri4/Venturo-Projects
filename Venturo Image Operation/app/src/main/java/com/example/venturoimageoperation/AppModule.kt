package com.example.venturoimageoperation

import com.example.venturoimageoperation.screens.imageoperation.ImageOperationViewModel
import com.example.venturoimageoperation.screens.imageoperation.ImageRepository
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    // Define your dependencies here
    single { ImageRepository() }
    viewModel { ImageOperationViewModel() }
}