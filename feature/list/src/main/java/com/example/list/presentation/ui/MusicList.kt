package com.example.list.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.R
import com.example.designsystem.ui.theme.SIDE_EFFECTS_KEY
import com.example.list.presentation.model.ListContract.Effect
import com.example.list.presentation.model.ListContract.Event
import com.example.list.presentation.model.ListContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
fun MusicList(
    source: String,
    paddingValues: PaddingValues,
    state: UiState,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
    onNavigationRequested: (navigationEffect: Effect.Navigation) -> Unit,
) {

    val query = remember { mutableStateOf("") }

    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        onEventSent(Event.GetData)
        effectFlow?.onEach { effect ->
            when (effect) {
                is Effect.Navigation.ToPlayer -> onNavigationRequested(effect)
            }
        }?.collect()
    }




    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = query.value,
            modifier = Modifier.padding(top = 16.dp),
            placeholder = {
                Text(text = "login")
            },
            singleLine = true,
            onValueChange = { newString ->
                query.value = newString
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.outline_manage_search_24),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        onEventSent(Event.Search(query.value))
                    }
                )
            }
        )
        LazyColumn {
            itemsIndexed(items = state.tracks, key = { _, item -> item.id }) { index, item ->
                TrackItem(item = item) {
                    onNavigationRequested(Effect.Navigation.ToPlayer(index, query.value, source))
                }
                if (index != state.tracks.lastIndex) {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}


@Preview
@Composable
private fun MusicListPreview() {
    Surface {
//        MusicList(PaddingValues())
    }
}