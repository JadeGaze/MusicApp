package com.example.list.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.common.mapper.toUiModel
import com.example.designsystem.ui.theme.BaseViewModel
import com.example.list.presentation.model.ListContract
import com.example.list.usecase.GetLocalTracksUseCase
import com.example.list.usecase.SearchLocalTracksUseCase
import kotlinx.coroutines.launch

class DownloadedViewModel(
    private val getLocalTracksUseCase: GetLocalTracksUseCase,
    private val searchUseCase: SearchLocalTracksUseCase,
) : BaseViewModel<ListContract.Event, ListContract.UiState, ListContract.Effect>() {
    override fun setInitialState(): ListContract.UiState {
        return ListContract.UiState(tracks = listOf(), isLoading = false, isError = false)
    }

    override fun handleEvents(event: ListContract.Event) {
        when (event) {
            ListContract.Event.GetData -> getDownloadedTracks()
            is ListContract.Event.Search -> search(event.query)
        }
    }

    private fun getDownloadedTracks() {
        viewModelScope.launch {
            getLocalTracksUseCase.invoke()
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
        viewModelScope.launch {
            searchUseCase.invoke(query)
                .onSuccess { tracks ->
                    setState { copy(tracks = tracks.map { it.toUiModel() }) }
                }
                .onFailure {
                    Log.d("SEARCH", "${it}")
                }

        }
    }
}