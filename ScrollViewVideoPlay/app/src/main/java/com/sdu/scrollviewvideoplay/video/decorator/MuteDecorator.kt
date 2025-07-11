package com.sdu.scrollviewvideoplay.video.decorator

class MuteDecorator(
    decoratedPlayer: BaseVideoPlayer,
    private val isMute: Boolean
) : VideoPlayerDecorator(decoratedPlayer) {

    override fun play() {
        decoratedPlayer.setMute(isMute)
        decoratedPlayer.play()
    }
}
