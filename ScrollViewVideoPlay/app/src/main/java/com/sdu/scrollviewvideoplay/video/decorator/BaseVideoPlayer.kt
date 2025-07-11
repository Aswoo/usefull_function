package com.sdu.scrollviewvideoplay.video.decorator

import androidx.media3.ui.PlayerView

interface BaseVideoPlayer {
    fun play()
    fun pause()
    fun release()
    fun setMute(isMute: Boolean)
    fun attachTo(playerView: PlayerView)
}
