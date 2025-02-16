package com.example.player.usecase

import com.example.common.mapper.toRelations
import com.example.common.presentation.model.TrackUiModel
import com.example.common.repository.TrackRepository
import com.example.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SaveTrackUseCase(
    private val repository: TrackRepository,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(track: TrackUiModel) {
        runSuspendCatching {
            withContext(dispatcher) {
                repository.saveTrack(track.toRelations())
            }
        }
    }
}