package com.example.list.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.common.mapper.toUiModel
import com.example.designsystem.ui.theme.BaseViewModel
import com.example.list.presentation.model.ListContract.Effect
import com.example.list.presentation.model.ListContract.Event
import com.example.list.presentation.model.ListContract.UiState
import com.example.list.usecase.GetChartUseCase
import com.example.list.usecase.SearchRemoteTracksUseCase
import kotlinx.coroutines.launch

class ChartViewModel(
    private val getChartUseCase: GetChartUseCase,
    private val searchUseCase: SearchRemoteTracksUseCase,
) : BaseViewModel<Event, UiState, Effect>() {
    override fun setInitialState(): UiState {
        return UiState(tracks = listOf(), isLoading = false, isError = false)
    }

    override fun handleEvents(event: Event) {
        when (event) {
            Event.GetData -> getChart()
            is Event.Search -> search(event.query)
        }
    }

    private fun getChart() {
        viewModelScope.launch {
            getChartUseCase.invoke()
                .onSuccess { tracks ->
                    setState { copy(tracks = tracks.map { it.toUiModel() }) }
                    Log.d("GET CHART", "${tracks}")
                }
                .onFailure {
                    Log.d("GET CHART", "${it}")
                }
        }
    }

    private fun search(query: String) {
        Log.d("SEARCH", "{it}")
        viewModelScope.launch {
            searchUseCase.invoke(query)
                .onSuccess { tracks ->
                    Log.d("SEARCH", "$tracks")
                    setState { copy(tracks = tracks.map { it.toUiModel() }) }
                }
                .onFailure {
                    Log.d("SEARCH", "${it}")
                }
        }
    }
}