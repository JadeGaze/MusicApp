package com.example.player.presentation.ui

import android.content.Intent
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import coil.compose.AsyncImage
import com.example.designsystem.R
import com.example.designsystem.ui.theme.Typography
import com.example.player.presentation.model.PlayerContract.Effect
import com.example.player.presentation.model.PlayerContract.Event
import com.example.player.presentation.model.PlayerContract.UiState
import com.example.player.presentation.ui.component.ProgressSlider
import com.example.player.presentation.ui.component.TimeDisplay
import com.example.utils.service.MyDownloadService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    paddingValues: PaddingValues,
    state: UiState,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
) {

    val context = LocalContext.current
    val currentIndex = state.player?.currentMediaItemIndex ?: 0
    var currentTrack = state.tracks.getOrNull(currentIndex)
    var currentPosition by remember { mutableLongStateOf(state.player?.currentPosition ?: 0L) }
    val duration = state.player?.duration ?: 0
    var playWhenReady by remember { mutableStateOf(state.player?.playWhenReady ?: false) }

    LaunchedEffect(state.player) {
        while (true) {
            delay(500)
            currentPosition = state.player?.currentPosition ?: 0L
        }
    }

    LaunchedEffect(currentIndex) {
        currentTrack = state.tracks.getOrNull(currentIndex)
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = currentTrack?.album?.cover,
            modifier = Modifier
                .padding(vertical = 32.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            error = painterResource(id = R.drawable.img),
            contentDescription = ""
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = currentTrack?.title ?: "Track name",
                    style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    text = currentTrack?.artist?.name ?: "Author name",
                    style = Typography.bodyLarge,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.outline_file_download_24),
                contentDescription = ""
            )
        }
        ProgressSlider(
            currentPosition = currentPosition,
            duration = duration
        ) { newProgress ->
            val newPosition = (newProgress * duration).toLong()
            state.player?.seekTo(newPosition)
            currentPosition = newPosition
        }
        TimeDisplay(currentPosition, duration)

        Row(
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onEventSent(Event.OnDownloadButtonClick)
                        val intent = Intent(context, MyDownloadService::class.java)
                        context.startForegroundService(intent)
                    },
                painter = painterResource(id = R.drawable.outline_file_download_24),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onEventSent(Event.OnPreviousButtonClick)
                    },
                painter = painterResource(id = R.drawable.outline_skip_previous_24),
                contentDescription = ""
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        playWhenReady = !playWhenReady
                        onEventSent(Event.OnPauseButtonClick)
                    },
                painter = if (playWhenReady) painterResource(id = R.drawable.outline_play_circle_24)
                else painterResource(id = R.drawable.outline_pause_circle_24),
                contentDescription = "",
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onEventSent(Event.OnNextButtonClick)
                    },
                painter = painterResource(id = R.drawable.outline_skip_next_24),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.outline_favorite_border_24),
                contentDescription = ""
            )

        }
    }
}