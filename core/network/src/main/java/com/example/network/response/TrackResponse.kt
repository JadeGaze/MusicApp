package com.example.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackResponse(
    val id: Long,
    val title: String,
    val duration: Int, //seconds
    val artist: ArtistResponse,
    val album: AlbumResponse,
    val preview: String,
)

@Serializable
data class ArtistResponse(
    val id: Long,
    val name: String,
)

@Serializable
data class AlbumResponse(
    val id: Long,
    val title: String,
    @SerialName("cover_big") val cover: String,
)

@Serializable
data class TracksContainer(
    val data: List<TrackResponse>,
)

@Serializable
data class ChartResponse(
    val tracks: TracksContainer,
)