package com.example.list.presentation.model

import com.example.common.presentation.model.TrackUiModel
import com.example.designsystem.ui.theme.ViewEvent
import com.example.designsystem.ui.theme.ViewSideEffect
import com.example.designsystem.ui.theme.ViewState

class ListContract {

    sealed class Event : ViewEvent {
        data class Search(val query: String) : Event()

        data object GetData : Event()
    }

    data class UiState(
        val tracks: List<TrackUiModel>,
        val isLoading: Boolean,
        val isError: Boolean,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class ToPlayer(val trackId: Int, val query: String, val source: String) :
                Navigation()
        }
    }

}