package com.example.music

import android.app.Application
import androidx.media3.common.util.UnstableApi
import com.example.db.dbModule
import com.example.list.listModule
import com.example.network.networkModule
import com.example.player.playerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@UnstableApi
class MusicApp : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MusicApp)
            modules(appModule, dbModule, networkModule, listModule, playerModule)
        }
    }
}