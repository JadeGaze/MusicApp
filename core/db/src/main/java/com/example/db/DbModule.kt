package com.example.db

import android.app.Application
import androidx.room.Room
import com.example.db.dao.AlbumDao
import com.example.db.dao.ArtistDao
import com.example.db.dao.TracksDao
import org.koin.dsl.module

fun provideDb(application: Application): AppDatabase =
    Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "music_app"
    ).build()

fun provideTracksDao(db: AppDatabase): TracksDao = db.tracksDao()
fun provideArtistsDao(db: AppDatabase): ArtistDao = db.artistsDao()
fun provideAlbumsDao(db: AppDatabase): AlbumDao = db.albumsDao()

val dbModule = module {
    single { provideDb(get()) }
    single { provideTracksDao(get()) }
    single { provideArtistsDao(get()) }
    single { provideAlbumsDao(get()) }
}