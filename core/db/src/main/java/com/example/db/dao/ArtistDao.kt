package com.example.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.db.entity.ArtistEntity

@Dao
interface ArtistDao {
    @Insert
    suspend fun saveArtist(artist: ArtistEntity)

    @Query("SELECT * FROM artists WHERE id = :id")
    suspend fun getArtistById(id: Long): ArtistEntity?
}