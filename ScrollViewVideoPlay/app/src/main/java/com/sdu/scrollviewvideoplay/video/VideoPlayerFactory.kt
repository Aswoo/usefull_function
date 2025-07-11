package com.sdu.scrollviewvideoplay.video

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.sdu.scrollviewvideoplay.video.decorator.*

object VideoPlayerFactory {

    fun createPlayer(
        context: Context,
        index: Int,            // 만약 여러 플레이어 풀 관리용이라면 사용
        videoUrl: String,
        mute: Boolean = false,
        subtitle: Boolean = false
    ): BaseVideoPlayer {

        // ExoPlayerPool에서 재사용 가능한 플레이어 가져오기 (없으면 새로 생성)
        val exoPlayer = ExoPlayerPool.getPlayer(context, index, videoUrl)

        // 기본 플레이어
        var player: BaseVideoPlayer = ExoVideoPlayer(exoPlayer)

        // 조건에 따라 데코레이터 감싸기
        if (mute) {
            player = MuteDecorator(player, isMute = mute)
        }

        if (subtitle) {
            player = SubtitleDecorator(player,subtitleUri = "subtitle")
        }

        return player
    }
}
