package com.example.music

import android.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import com.example.utils.service.MyDownloadService
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File
import java.util.concurrent.Executor

@UnstableApi
val appModule = module {

    single<DatabaseProvider> { StandaloneDatabaseProvider(androidApplication().applicationContext) }
    single { File(androidApplication().applicationContext.getExternalFilesDir(null), "MusicApp") }
    single<Cache> { SimpleCache(get(), NoOpCacheEvictor(), get()) }
    factory<DataSource.Factory>(named("remote")) { DefaultHttpDataSource.Factory() }
    factory { Executor(Runnable::run) }
    factory<DownloadManager.Listener> {
        object : DownloadManager.Listener {
            override fun onDownloadChanged(
                downloadManager: DownloadManager,
                download: Download,
                finalException: Exception?,
            ) {
                when (download.state) {
                    Download.STATE_COMPLETED -> {

                        Log.d("DOWNLOAD", "Completes;")
                    }

                    Download.STATE_FAILED -> {
                        Log.d("DOWNLOAD", "${finalException?.message}")
                    }

                    else -> {
                        Log.d("DOWNLOAD", "state: ${download.state}")
                    }
                }
            }
        }
    }

    factory<DataSource.Factory>(named("local")) {
        CacheDataSource.Factory()
            .setCache(get())
            .setUpstreamDataSourceFactory(get(named("remote")))
    }

    single<DownloadManager> {
        DownloadManager(
            androidApplication().applicationContext,
            get(),
            get(),
            get(named("local")),
            get(),
        ).apply {
            addListener(get())
        }
    }

    single<MyDownloadService> {
        MyDownloadService()
    }

}