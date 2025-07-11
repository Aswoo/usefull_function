package com.sdu.scrollviewvideoplay.video.decorator

import android.net.Uri
import androidx.media3.common.C
import androidx.media3.common.MediaItem

class SubtitleDecorator(
    decoratedPlayer: BaseVideoPlayer,
    private val subtitleUri: String
) : VideoPlayerDecorator(decoratedPlayer) {

    override fun play() {
        if (decoratedPlayer is ExoVideoPlayer) {
            val mediaItem = decoratedPlayer.exoPlayer.currentMediaItem ?: return
            val subtitle = MediaItem.SubtitleConfiguration.Builder(Uri.parse(subtitleUri))
                .setMimeType("text/vtt")
                .setLanguage("en")
                .setSelectionFlags(C.SELECTION_FLAG_DEFAULT)
                .build()

            val newMediaItem = mediaItem.buildUpon()
                .setSubtitleConfigurations(listOf(subtitle))
                .build()

            decoratedPlayer.exoPlayer.setMediaItem(newMediaItem)
            decoratedPlayer.exoPlayer.prepare()
        }
        decoratedPlayer.play()
    }
}
