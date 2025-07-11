package com.sdu.scrollviewvideoplay.video.decorator

import androidx.media3.ui.PlayerView

open class VideoPlayerDecorator(
    protected val decoratedPlayer: BaseVideoPlayer
) : BaseVideoPlayer {
    override fun play() = decoratedPlayer.play()
    override fun pause() = decoratedPlayer.pause()
    override fun release() = decoratedPlayer.release()
    override fun setMute(isMute: Boolean) = decoratedPlayer.setMute(isMute)
    override fun attachTo(playerView: PlayerView) = decoratedPlayer.attachTo(playerView)
}