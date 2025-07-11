package com.sdu.scrollviewvideoplay

import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.sdu.scrollviewvideoplay.video.VideoPlayerFactory
import com.sdu.scrollviewvideoplay.video.decorator.BaseVideoPlayer

class VideoListViewModel : ViewModel() {
    // 각 비디오 URL 리스트
    val videoUrls = listOf(
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4"
    )
    val players = mutableMapOf<Int, BaseVideoPlayer>()

    fun getOrCreatePlayer(context: Context, index: Int, mute: Boolean = false, subtitle: Boolean = false): BaseVideoPlayer {
        return players.getOrPut(index) {
            val url = videoUrls[index]
            VideoPlayerFactory.createPlayer(context, index, url, mute, subtitle)
        }
    }

    // 각 비디오 재생 상태 저장 (true: 재생중, false: 일시정지)
    val playingStates = mutableStateMapOf<Int, Boolean>()

    // 특정 인덱스 재생 상태 변경
    fun setPlaying(index: Int, isPlaying: Boolean) {
        playingStates[index] = isPlaying
    }

    // 특정 인덱스 재생 상태 확인
    fun isPlaying(index: Int): Boolean {
        return playingStates[index] ?: false
    }
}
