package com.sdu.scrollviewvideoplay

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.sdu.scrollviewvideoplay.video.decorator.BaseVideoPlayer

@Composable
fun VideoCard(
    player: BaseVideoPlayer,
    isVisible: Boolean,
    isPlaying: Boolean,
    onPlayPause: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var localPlayState by remember { mutableStateOf(isPlaying) }

    // 외부 상태 반영 (뷰모델 등)
    LaunchedEffect(isPlaying) {
        localPlayState = isPlaying
    }

    // 재생 상태 처리
    LaunchedEffect(isVisible, localPlayState) {
        if (isVisible && localPlayState) {
            player.play()
        } else {
            player.pause()
        }
    }

    // 리소스 정리
    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    // UI
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp) // ✅ 화면 더 크게
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            AndroidView(
                factory = {
                    PlayerView(context).apply {
                        useController = false
                        player.attachTo(this)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    val newState = !localPlayState
                    localPlayState = newState       // 내부 반영
                    onPlayPause(newState)           // 외부 반영 (ViewModel 등)
                }) {
                    Text(if (localPlayState) "Pause" else "Play")
                }
            }
        }
    }
}