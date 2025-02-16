package com.example.player

import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.player.presentation.PlayerViewModel
import com.example.player.usecase.SaveTrackUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

@UnstableApi
val playerModule = module {

    single<ExoPlayer> {
        ExoPlayer.Builder(androidContext()).setMediaSourceFactory(
            DefaultMediaSourceFactory(androidContext()).setDataSourceFactory(get(named("local")))
        ).build()
    }

    factory { SaveTrackUseCase(get(), get()) }

    viewModel { (trackId: Int, query: String, source: String) ->
        PlayerViewModel(
            trackId,
            query,
            source,
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            androidContext()
        )
    }

}