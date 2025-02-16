package com.example.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.designsystem.R

sealed class Destination(
    val route: String,
    @DrawableRes val iconId: Int,
    @StringRes val title: Int,
) {

    data object Catalog : Destination(
        route = "catalog",
        iconId = R.drawable.outline_manage_search_24,
        title = R.string.catalog
    )

    data object CurrentTrack : Destination(
        route = "current/{${TRACK_ID}}/{${QUERY}}/{${SOURCE}}",
        iconId = R.drawable.outline_play_circle_24,
        title = R.string.current
    )

    data object Downloaded : Destination(
        route = "my tracks",
        iconId = R.drawable.outline_file_download_24,
        title = R.string.my_tracks
    )

    companion object {
        val toList = listOf(Catalog, Downloaded)
    }
}