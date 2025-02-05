package com.venturo.venturoreport

import android.app.Application
import com.venturo.venturoreport.repository.OrderRepository
import com.venturo.venturoreport.ui.screens.order.components.OrderDataSource
import com.venturo.venturoreport.ui.screens.order.components.OrderViewModel
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class VenturoReportApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VenturoReportApplication)
            modules(appModule)
        }
    }
}

val appModule = module {
    // Define your ViewModel
    viewModel { OrderViewModel(get()) }

    single<OrderRepository> { OrderRepository() }

    // Define your data source
    single { OrderDataSource() }
}