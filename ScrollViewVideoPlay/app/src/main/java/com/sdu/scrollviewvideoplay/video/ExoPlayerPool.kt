package com.sdu.scrollviewvideoplay.video

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import java.util.LinkedHashMap

object ExoPlayerPool {

    private val playerMap = LinkedHashMap<Int, ExoPlayer>()
    private const val MAX_POOL_SIZE = 3

    fun getPlayer(context: Context, index: Int, videoUrl: String): ExoPlayer {
        // 이미 존재하면 반환
        if (playerMap.containsKey(index)) return playerMap[index]!!

        // 초과 시 가장 오래된 것 제거
        if (playerMap.size >= MAX_POOL_SIZE) {
            val firstKey = playerMap.keys.first()
            playerMap[firstKey]?.release()
            playerMap.remove(firstKey)
        }

        // 새 플레이어 생성 및 등록
        val player = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = true // 자동 재생 활성화
        }

        playerMap[index] = player
        return player
    }

    fun releasePlayer(index: Int) {
        playerMap[index]?.release()
        playerMap.remove(index)
    }

    fun releaseAll() {
        playerMap.values.forEach { it.release() }
        playerMap.clear()
    }
}
