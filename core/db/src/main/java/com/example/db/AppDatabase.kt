package com.example.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.db.dao.AlbumDao
import com.example.db.dao.ArtistDao
import com.example.db.dao.TracksDao
import com.example.db.entity.AlbumEntity
import com.example.db.entity.ArtistEntity
import com.example.db.entity.TrackEntity

@Database(
    version = 1,
    entities = [TrackEntity::class, AlbumEntity::class, ArtistEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tracksDao(): TracksDao
    abstract fun artistsDao(): ArtistDao
    abstract fun albumsDao(): AlbumDao
}