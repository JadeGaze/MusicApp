package com.example.list.usecase

import com.example.common.repository.TrackRepository
import com.example.network.response.TrackResponse
import com.example.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetChartUseCase(
    private val repository: TrackRepository,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): Result<List<TrackResponse>> {
        return runSuspendCatching {
            withContext(dispatcher) {
                repository.getChart()
            }
        }
    }
}