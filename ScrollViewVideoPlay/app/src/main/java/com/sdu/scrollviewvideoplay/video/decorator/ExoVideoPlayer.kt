
package com.sdu.scrollviewvideoplay.video.decorator

import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class ExoVideoPlayer(
    internal val exoPlayer: ExoPlayer
) : BaseVideoPlayer {

    override fun play() {
        exoPlayer.playWhenReady = true
    }

    override fun pause() {
        exoPlayer.playWhenReady = false
    }

    override fun release() {
        exoPlayer.release()
    }

    override fun setMute(isMute: Boolean) {
        exoPlayer.volume = if (isMute) 0f else 1f
    }

    override fun attachTo(playerView: PlayerView) {
        playerView.player = exoPlayer
    }
}
