package com.example.list.usecase

import com.example.common.repository.TrackRepository
import com.example.network.response.TrackResponse
import com.example.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SearchRemoteTracksUseCase(
    private val repository: TrackRepository,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(query: String): Result<List<TrackResponse>> {
        return runSuspendCatching {
            withContext(dispatcher) {
                repository.searchRemoteTracks(query)
            }
        }
    }
}
