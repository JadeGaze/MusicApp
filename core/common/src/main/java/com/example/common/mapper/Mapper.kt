package com.example.common.mapper

import com.example.common.presentation.model.AlbumUiModel
import com.example.common.presentation.model.ArtistUiModel
import com.example.common.presentation.model.TrackUiModel
import com.example.db.entity.AlbumEntity
import com.example.db.entity.ArtistEntity
import com.example.db.entity.TrackEntity
import com.example.db.entity.TrackWithRelations
import com.example.network.response.AlbumResponse
import com.example.network.response.ArtistResponse
import com.example.network.response.TrackResponse


fun TrackUiModel.toEntity(): TrackEntity {
    return TrackEntity(
        id = id,
        title = title,
        duration = duration,
        artistId = artist.id,
        albumId = album.id,
        preview = preview
    )
}

fun TrackUiModel.toRelations(): TrackWithRelations = TrackWithRelations(
    track = this.toEntity(),
    artist = this.artist.toEntity(),
    album = this.album.toEntity()
)

fun TrackWithRelations.toUiModel(): TrackUiModel = TrackUiModel(
    id = track.id,
    title = track.title,
    duration = track.duration,
    artist = artist.toUiModel(),
    album = album.toUiModel(),
    preview = track.preview
)

fun ArtistEntity.toUiModel(): ArtistUiModel = ArtistUiModel(
    id = id,
    name = name
)

fun TrackResponse.toUiModel(): TrackUiModel = TrackUiModel(
    id = id,
    title = title,
    duration = duration * 1000L,
    artist = artist.toUiModel(),
    album = album.toUiModel(),
    preview = preview
)

fun ArtistResponse.toUiModel(): ArtistUiModel = ArtistUiModel(
    id = id, name = name
)

fun AlbumResponse.toUiModel(): AlbumUiModel = AlbumUiModel(
    id = id, title = title, cover = cover
)

fun AlbumEntity.toUiModel(): AlbumUiModel = AlbumUiModel(
    id = id,
    title = title,
    cover = cover
)

fun ArtistUiModel.toEntity(): ArtistEntity = ArtistEntity(
    id = id,
    name = name
)


fun AlbumUiModel.toEntity(): AlbumEntity = AlbumEntity(
    id = id,
    title = title,
    cover = cover
)
