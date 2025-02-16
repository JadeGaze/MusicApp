package com.example.utils.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.scheduler.Scheduler
import com.example.designsystem.R
import org.koin.android.ext.android.inject

@UnstableApi
class MyDownloadService :
    DownloadService(
        FOREGROUND_NOTIFICATION_ID,
        DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
        FOREGROUND_NOTIFICATION_CHANNEL_ID,
        R.string.app_name,
        R.string.app_describe
    ) {

    private val myDownloadManager: DownloadManager by inject()

    override fun getDownloadManager(): DownloadManager {
        return myDownloadManager
    }

    override fun getScheduler(): Scheduler? {
        return null
    }

    override fun getForegroundNotification(
        downloads: MutableList<Download>,
        notMetRequirements: Int,
    ): Notification {
        val channel = NotificationChannel(
            FOREGROUND_NOTIFICATION_CHANNEL_ID,
            "Download Service",
            NotificationManager.IMPORTANCE_LOW
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return Notification.Builder(this, FOREGROUND_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.downloading))
            .setContentText(getString(R.string.download_in_progress))
            .setSmallIcon(R.drawable.outline_file_download_24)
            .build()
    }

    companion object {
        private const val FOREGROUND_NOTIFICATION_ID = 1
        private const val FOREGROUND_NOTIFICATION_CHANNEL_ID = "1"
    }
}