package com.sdu.scrollviewvideoplay

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import com.sdu.scrollviewvideoplay.video.ExoPlayerPool
import kotlin.math.max
import kotlin.math.min

@Composable
fun VideoListScreen(
    modifier: Modifier,
    viewModel: VideoListViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val players = remember {
        viewModel.videoUrls.map {
            ExoPlayer.Builder(context).build()
        }
    }

    val listState = rememberLazyListState()

    val playingIndex by remember {
        derivedStateOf {
            val visibleItems = listState.layoutInfo.visibleItemsInfo

            when (visibleItems.size) {
                0 -> -1 // 아무것도 보이지 않으면 정지
                1 -> visibleItems.first().index
                2 -> {
                    visibleItems.firstOrNull {
                        val itemTop = it.offset
                        val itemBottom = it.offset + it.size
                        val visibleHeight = min(itemBottom, listState.layoutInfo.viewportEndOffset) -
                                max(itemTop, listState.layoutInfo.viewportStartOffset)
                        visibleHeight >= it.size * 0.7
                    }?.index ?: visibleItems.first().index
                }
                else -> visibleItems[visibleItems.size / 2].index
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        state = listState
    ) {
        itemsIndexed(viewModel.videoUrls) { index, videoUrl ->
            val isVisible = index == playingIndex

            LaunchedEffect(isVisible) {
                viewModel.setPlaying(index, isVisible)
            }
            val player = viewModel.getOrCreatePlayer(context, index)

            VideoCard(
                player = player,
                isVisible = isVisible,
                isPlaying = viewModel.isPlaying(index),
                onPlayPause = {
                    viewModel.setPlaying(index, isPlaying = it)
                }
            )
        }
    }
}
