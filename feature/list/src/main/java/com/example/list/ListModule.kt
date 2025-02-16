package com.example.list

import com.example.common.repository.TrackRepository
import com.example.list.presentation.ChartViewModel
import com.example.list.presentation.DownloadedViewModel
import com.example.list.usecase.GetChartUseCase
import com.example.list.usecase.GetLocalTracksUseCase
import com.example.list.usecase.SearchLocalTracksUseCase
import com.example.list.usecase.SearchRemoteTracksUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listModule = module {

    single { Dispatchers.IO }
    factory { GetChartUseCase(get(), get()) }
    factory { GetLocalTracksUseCase(get(), get()) }
    factory { SearchLocalTracksUseCase(get(), get()) }
    factory { SearchRemoteTracksUseCase(get(), get()) }
    factory { TrackRepository(get(), get(), get(), get()) }

    viewModel { ChartViewModel(get(), get()) }
    viewModel { DownloadedViewModel(get(), get()) }
}