package com.example.common.presentation.model

data class TrackUiModel(
    val id: Long,
    val title: String,
    val duration: Long,
    val artist: ArtistUiModel,
    val album: AlbumUiModel,
    val preview: String,
) {
    companion object {
        fun getDefaultList(): List<TrackUiModel> =
            listOf(
                TrackUiModel(
                    id = 1,
                    title = "11111",
                    album = AlbumUiModel.getDefault(),
                    artist = ArtistUiModel.getDefault(),
                    duration = 5106,
                    preview = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
                ),
                TrackUiModel(
                    id = 2,
                    title = "22222",
                    album = AlbumUiModel.getDefault(),
                    artist = ArtistUiModel.getDefault(),
                    duration = 5106,
                    preview = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
                ),
                TrackUiModel(
                    id = 3,
                    title = "33333",
                    album = AlbumUiModel.getDefault(),
                    artist = ArtistUiModel.getDefault(),
                    duration = 5106,
                    preview = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
                ),
                TrackUiModel(
                    id = 4,
                    title = "44444",
                    album = AlbumUiModel.getDefault(),
                    artist = ArtistUiModel.getDefault(),
                    duration = 5106,
                    preview = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
                ),
            )
    }
}


data class ArtistUiModel(
    val id: Long,
    val name: String,
) {
    companion object {
        fun getDefault() =
            ArtistUiModel(id = 1, name = "Artist 001")
    }
}

data class AlbumUiModel(
    val id: Long,
    val title: String,
    val cover: String,
) {
    companion object {
        fun getDefault() =
            AlbumUiModel(
                id = 1, title = "Album 001", cover = ""
            )
    }
}