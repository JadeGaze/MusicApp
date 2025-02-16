package com.example.player.presentation

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import com.example.common.mapper.toUiModel
import com.example.designsystem.ui.theme.BaseViewModel
import com.example.list.usecase.GetChartUseCase
import com.example.list.usecase.GetLocalTracksUseCase
import com.example.list.usecase.SearchLocalTracksUseCase
import com.example.list.usecase.SearchRemoteTracksUseCase
import com.example.player.presentation.model.PlayerContract.Effect
import com.example.player.presentation.model.PlayerContract.Event
import com.example.player.presentation.model.PlayerContract.UiState
import com.example.player.usecase.SaveTrackUseCase
import com.example.utils.service.MyDownloadService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PlayerViewModel @UnstableApi constructor(
    private val id: Int,
    private val query: String,
    private val source: String,
    private val player: ExoPlayer,
    private val saveTrackUseCase: SaveTrackUseCase,
    private val getChartUseCase: GetChartUseCase,
    private val searchRemoteTracksUseCase: SearchRemoteTracksUseCase,
    private val searchLocalTracksUseCase: SearchLocalTracksUseCase,
    private val getLocalTracksUseCase: GetLocalTracksUseCase,
    private val context: Context,
) : BaseViewModel<Event, UiState, Effect>() {


    init {
        loadTrack()
    }

    override fun setInitialState(): UiState {
        return UiState(
            tracks = listOf(),
            player = player,
            isLoading = false,
            isError = false
        )
    }

    override fun handleEvents(event: Event) {
        when (event) {
            Event.LoadTrack -> loadTrack()
            Event.OnDownloadButtonClick -> downloadTrack()
            Event.OnNextButtonClick -> nextTrack()
            Event.OnPauseButtonClick -> pauseTrack()
            Event.OnPlayButtonClick -> playTrack()
            Event.OnPreviousButtonClick -> previousTrack()
        }
    }

    private fun loadTrack() {
        viewModelScope.launch {
            getTracks()
            player.prepare()
            delay(500)
            viewState.value.tracks.forEach { track ->
                player.addMediaItem(MediaItem.fromUri(track.preview))
            }
            player.seekTo(id, 0L)
            player.playWhenReady = true
            player.repeatMode = Player.REPEAT_MODE_ALL
            setState {
                copy(player = this@PlayerViewModel.player)
            }
        }
    }

    private fun getTracks() {
        player.clearMediaItems()
        viewModelScope.launch {
            if (source == "local") {
                getLocalTracks()
            } else {
                getRemoteTracks()
            }
        }
    }

    private fun getRemoteTracks() {
        viewModelScope.launch {
            if (query.isEmpty()) {
                getChartUseCase.invoke()
                    .onSuccess { tracks ->
                        setState { copy(tracks = tracks.map { it.toUiModel() }) }
                    }
            } else {
                searchRemoteTracksUseCase.invoke(query)
                    .onSuccess { tracks ->
                        setState { copy(tracks = tracks.map { it.toUiModel() }) }
                    }
            }
        }
    }

    private fun getLocalTracks() {
        viewModelScope.launch {
            if (query.isEmpty()) {
                getLocalTracksUseCase.invoke()
                    .onSuccess { tracks ->
                        setState { copy(tracks = tracks.map { it.toUiModel() }) }
                    }
            } else {
                searchLocalTracksUseCase.invoke(query)
                    .onSuccess { tracks ->
                        setState { copy(tracks = tracks.map { it.toUiModel() }) }
                    }
            }
        }
    }

    @OptIn(UnstableApi::class)
    private fun downloadTrack() {
        val currentPosition = player.currentMediaItemIndex
        val currentTrack = viewState.value.tracks[currentPosition]
        val downloadRequest =
            DownloadRequest.Builder(
                currentTrack.id.toString(),
                Uri.parse("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
            )
                .setMimeType(MimeTypes.AUDIO_MPEG)
                .build()

        DownloadService.sendAddDownload(
            context,
            MyDownloadService::class.java,
            downloadRequest,
            true
        )

        viewModelScope.launch {
            saveTrackUseCase.invoke(currentTrack)
        }

    }

    private fun nextTrack() {
        viewModelScope.launch { player.seekToNextMediaItem() }
    }

    private fun pauseTrack() {
        viewModelScope.launch { player.playWhenReady = !player.playWhenReady }
    }

    private fun playTrack() {
        viewModelScope.launch { player.playWhenReady = !player.playWhenReady }
    }

    private fun previousTrack() {
        viewModelScope.launch { player.seekToPreviousMediaItem() }
    }
}