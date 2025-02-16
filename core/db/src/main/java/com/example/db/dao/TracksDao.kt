package com.example.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.db.entity.TrackEntity
import com.example.db.entity.TrackWithRelations

@Dao
interface TracksDao {

    @Insert
    suspend fun saveTrack(track: TrackEntity)

    @Query("SELECT * FROM tracks")
    suspend fun getAllTracks(): List<TrackWithRelations>?

    @Query("SELECT * FROM tracks WHERE title LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<TrackWithRelations>?
}
