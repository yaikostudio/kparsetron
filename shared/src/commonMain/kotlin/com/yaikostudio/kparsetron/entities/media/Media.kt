package com.yaikostudio.kparsetron.entities.media

sealed class Media {
    data class Video(
        val streamingType: StreamingType,
        val caption: String? = null,
        val container: VideoContainer? = null,
        val videoCodec: VideoCodec? = null,
        val is3D: Boolean = false,
        val height: Int? = null,
        val width: Int? = null,
        val fps: Int? = null,
        val audioCodec: AudioCodec? = null, // null if no audio
        val audioBitrate: Int? = null,
    ): Media()

    data class Audio(
        val streamingType: StreamingType,
        val container: AudioContainer,
        val codec: AudioCodec,
        val caption: String,
        val bitrate: Int? = null,
    ): Media()
}
