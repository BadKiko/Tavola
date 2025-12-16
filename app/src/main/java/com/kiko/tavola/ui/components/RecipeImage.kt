package com.kiko.tavola.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material.icons.outlined.Image
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade


@Composable
fun RecipeImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            // Всегда используем одно и то же изображение для всего UI
            .data("https://icdn.lenta.ru/images/2024/06/28/12/20240628123130464/wide_16_9_3e207633180b39e720c3f4c4fd23364e.jpg")
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        placeholder = rememberVectorPainter(Icons.Outlined.Image),
        error = rememberVectorPainter(Icons.Outlined.BrokenImage),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)),
        contentScale = contentScale
    )
}

