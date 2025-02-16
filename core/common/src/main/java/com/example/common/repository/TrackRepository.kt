package com.example.common.repository

import com.example.db.dao.AlbumDao
import com.example.db.dao.ArtistDao
import com.example.db.dao.TracksDao
import com.example.db.entity.TrackWithRelations
import com.example.network.DeezerApi
import com.example.network.response.TrackResponse

class TrackRepository(
    private val tracksDao: TracksDao,
    private val albumDao: AlbumDao,
    private val artistDao: ArtistDao,
    private val deezerApi: DeezerApi,
) {
    suspend fun saveTrack(track: TrackWithRelations) {
        albumDao.saveAlbum(track.album)
        artistDao.saveArtist(track.artist)
        tracksDao.saveTrack(track.track)
    }

    suspend fun getLocalTracks(): List<TrackWithRelations> {
        return tracksDao.getAllTracks() ?: listOf()
    }

    suspend fun getChart(): List<TrackResponse> {
        return deezerApi.getTracks()?.tracks?.data ?: listOf()
    }

    suspend fun searchLocalTracks(query: String): List<TrackWithRelations> {
        return tracksDao.search(query) ?: listOf()
    }

    suspend fun searchRemoteTracks(query: String): List<TrackResponse> {
        return deezerApi.searchTracks(query)?.data ?: listOf()
    }
}