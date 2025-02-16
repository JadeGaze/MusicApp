package com.example.network

import com.example.network.response.ChartResponse
import com.example.network.response.TrackResponse
import com.example.network.response.TracksContainer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApi {

    @GET("search")
    suspend fun searchTracks(
        @Query("q") query: String,
    ): TracksContainer?

    @GET("chart")
    suspend fun getTracks(): ChartResponse?

    @GET("track/{id}")
    suspend fun getTrack(
        @Path("id") trackId: Int,
    )

}