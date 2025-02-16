package com.example.list.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.common.presentation.model.TrackUiModel
import com.example.designsystem.R
import com.example.designsystem.ui.theme.Typography

@Composable
fun TrackItem(item: TrackUiModel, onItemClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable {
                onItemClicked()
            }
    ) {
        AsyncImage(
            model = item.album.cover,
            modifier = Modifier.size(56.dp),
            error = painterResource(id = R.drawable.img),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.title,
                style = Typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = item.artist.name,
                style = Typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun TrackItemPreview() {
    Surface {
        TrackItem(TrackUiModel.getDefaultList()[0]) {}
    }
}