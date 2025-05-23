package com.example.list.usecase

import com.example.common.repository.TrackRepository
import com.example.db.entity.TrackWithRelations
import com.example.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SearchLocalTracksUseCase(
    private val repository: TrackRepository,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(query: String): Result<List<TrackWithRelations>> {
        return runSuspendCatching {
            withContext(dispatcher) {
                repository.searchLocalTracks(query)
            }
        }
    }
}
