package com.example.player.presentation.model

import androidx.media3.exoplayer.ExoPlayer
import com.example.common.presentation.model.TrackUiModel
import com.example.designsystem.ui.theme.ViewEvent
import com.example.designsystem.ui.theme.ViewSideEffect
import com.example.designsystem.ui.theme.ViewState

class PlayerContract {

    sealed class Event : ViewEvent {
        data object LoadTrack : Event()
        data object OnPauseButtonClick : Event()
        data object OnPlayButtonClick : Event()
        data object OnNextButtonClick : Event()
        data object OnPreviousButtonClick : Event()
        data object OnDownloadButtonClick : Event()
    }

    data class UiState(
        val tracks: List<TrackUiModel>,
        val player: ExoPlayer?,
        val isLoading: Boolean,
        val isError: Boolean,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object TrackLoaded : Effect()
        data object PlayerPrepared : Effect()
        data object PlayerStopped : Effect()
        data object PlayerPlayed : Effect()
        data object TrackDownloaded : Effect()
        data class SomethingWentWrong(val message: String) : Effect()
    }

}