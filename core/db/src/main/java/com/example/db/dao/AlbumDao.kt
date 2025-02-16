package com.example.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.db.entity.AlbumEntity

@Dao
interface AlbumDao {
    @Insert
    suspend fun saveAlbum(album: AlbumEntity)

    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getAlbumById(id: Long): AlbumEntity?
}