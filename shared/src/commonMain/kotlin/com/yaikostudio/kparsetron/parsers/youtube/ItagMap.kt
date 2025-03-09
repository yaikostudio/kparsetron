package com.yaikostudio.kparsetron.parsers.youtube

import com.yaikostudio.kparsetron.entities.media.AudioCodec
import com.yaikostudio.kparsetron.entities.media.AudioContainer
import com.yaikostudio.kparsetron.entities.media.Media
import com.yaikostudio.kparsetron.entities.media.StreamingType
import com.yaikostudio.kparsetron.entities.media.VideoCodec
import com.yaikostudio.kparsetron.entities.media.VideoContainer

// merged data from youtube-dl and https://gist.github.com/AgentOak/34d47c65b1d28829bb17c24c04a0096f
val ITAG_MAP = mapOf<String, Media>(
    "5" to Media.Video(streamingType = StreamingType.DIRECT, caption = "FLV 240p", container = VideoContainer.FLV, width = 400, height = 240, audioCodec = AudioCodec.MP3, audioBitrate = 64, videoCodec = VideoCodec.H263),
    "6" to Media.Video(streamingType = StreamingType.DIRECT, caption = "FLV 270p", container = VideoContainer.FLV, width = 450, height = 270, audioCodec = AudioCodec.MP3, audioBitrate = 64, videoCodec = VideoCodec.H263),
    "13" to Media.Video(streamingType = StreamingType.DIRECT, caption = "3GP", container = VideoContainer.THREE_GP, audioCodec = AudioCodec.AAC, videoCodec = VideoCodec.MP4V),
    "17" to Media.Video(streamingType = StreamingType.DIRECT, caption = "3GP 144p", container = VideoContainer.THREE_GP, width = 176, height = 144, audioCodec = AudioCodec.AAC, audioBitrate = 24, videoCodec = VideoCodec.MP4V),
    "18" to Media.Video(streamingType = StreamingType.DIRECT, caption = "MP4 360p", container = VideoContainer.MP4, width = 640, height = 360, audioCodec = AudioCodec.AAC, audioBitrate = 96, videoCodec = VideoCodec.H264),
    "22" to Media.Video(streamingType = StreamingType.DIRECT, caption = "MP4 720p", container = VideoContainer.MP4, width = 1280, height = 720, audioCodec = AudioCodec.AAC, audioBitrate = 192, videoCodec = VideoCodec.H264),
    "34" to Media.Video(streamingType = StreamingType.DIRECT, caption = "FLV 360p", container = VideoContainer.FLV, width = 640, height = 360, audioCodec = AudioCodec.AAC, audioBitrate = 128, videoCodec = VideoCodec.H264),
    "35" to Media.Video(streamingType = StreamingType.DIRECT, caption = "FLV 480p", container = VideoContainer.FLV, width = 854, height = 480, audioCodec = AudioCodec.AAC, audioBitrate = 128, videoCodec = VideoCodec.H264),
    // itag 36 videos are either 320x180 (BaW_jenozKc) or 320x240 (__2ABJjxzNo), abr varies as well
    "36" to Media.Video(streamingType = StreamingType.DIRECT, caption = "3GP 320p", container = VideoContainer.THREE_GP, width = 320, audioCodec = AudioCodec.AAC, videoCodec = VideoCodec.MP4V),
    "37" to Media.Video(streamingType = StreamingType.DIRECT, caption = "MP4 H.264 1080p", container = VideoContainer.MP4, width = 1920, height = 1080, audioCodec = AudioCodec.AAC, audioBitrate = 192, videoCodec = VideoCodec.H264),
    "38" to Media.Video(streamingType = StreamingType.DIRECT, caption = "MP4 H.264 3072p", container = VideoContainer.MP4, width = 4096, height = 3072, audioCodec = AudioCodec.AAC, audioBitrate = 192, videoCodec = VideoCodec.H264),
    "43" to Media.Video(streamingType = StreamingType.DIRECT, caption = "VP8 360p", container = VideoContainer.WEBM, width = 640, height = 360, audioCodec = AudioCodec.VORBIS, audioBitrate = 128, videoCodec = VideoCodec.VP8),
    "44" to Media.Video(streamingType = StreamingType.DIRECT, caption = "WEBM 480p", container = VideoContainer.WEBM, width = 854, height = 480, audioCodec = AudioCodec.VORBIS, audioBitrate = 128, videoCodec = VideoCodec.VP8),
    "45" to Media.Video(streamingType = StreamingType.DIRECT, caption = "WEBM 720p", container = VideoContainer.WEBM, width = 1280, height = 720, audioCodec = AudioCodec.VORBIS, audioBitrate = 192, videoCodec = VideoCodec.VP8),
    "46" to Media.Video(streamingType = StreamingType.DIRECT, caption = "WEBM 1080p", container = VideoContainer.WEBM, width = 1920, height = 1080, audioCodec = AudioCodec.VORBIS, audioBitrate = 192, videoCodec = VideoCodec.VP8),
    "59" to Media.Video(streamingType = StreamingType.DIRECT, caption = "MP4 480p", container = VideoContainer.MP4, width = 854, height = 480, audioCodec = AudioCodec.AAC, audioBitrate = 128, videoCodec = VideoCodec.H264),
    "78" to Media.Video(streamingType = StreamingType.DIRECT, caption = "MP4 480p", container = VideoContainer.MP4, width = 854, height = 480, audioCodec = AudioCodec.AAC, audioBitrate = 128, videoCodec = VideoCodec.H264),

    // 3D videos
    "82" to Media.Video(streamingType = StreamingType.DIRECT, caption = "MP4 360p 3D", container = VideoContainer.MP4, height = 360, is3D = true, audioCodec = AudioCodec.AAC, audioBitrate = 128, videoCodec = VideoCodec.H264),
    "83" to Media.Video(streamingType = StreamingType.DIRECT, caption = "MP4 480p 3D", container = VideoContainer.MP4, height = 480, is3D = true, audioCodec = AudioCodec.AAC, audioBitrate = 128, videoCodec = VideoCodec.H264),
    "84" to Media.Video(streamingType = StreamingType.DIRECT, caption = "MP4 720p 3D", container = VideoContainer.MP4, height = 720, is3D = true, audioCodec = AudioCodec.AAC, audioBitrate = 192, videoCodec = VideoCodec.H264),
    "85" to Media.Video(streamingType = StreamingType.DIRECT, caption = "MP4 1080p 3D", container = VideoContainer.MP4, height = 1080, is3D = true, audioCodec = AudioCodec.AAC, audioBitrate = 192, videoCodec = VideoCodec.H264),
    "100" to Media.Video(streamingType = StreamingType.DIRECT, caption = "WEBM 360p 3D", container = VideoContainer.WEBM, height = 360, is3D = true, audioCodec = AudioCodec.VORBIS, audioBitrate = 128, videoCodec = VideoCodec.VP8),
    "101" to Media.Video(streamingType = StreamingType.DIRECT, caption = "WEBM 480p 3D", container = VideoContainer.WEBM, height = 480, is3D = true, audioCodec = AudioCodec.VORBIS, audioBitrate = 192, videoCodec = VideoCodec.VP8),
    "102" to Media.Video(streamingType = StreamingType.DIRECT, caption = "WEBM 720p 3D", container = VideoContainer.WEBM, height = 720, is3D = true, audioCodec = AudioCodec.VORBIS, audioBitrate = 192, videoCodec = VideoCodec.VP8),

    // Apple HTTP Live Streaming
    "91" to Media.Video(streamingType = StreamingType.HLS, caption = "MP4 H264 144p", container = VideoContainer.MP4, height = 144, audioCodec = AudioCodec.AAC, audioBitrate = 48, videoCodec = VideoCodec.H264),
    "92" to Media.Video(streamingType = StreamingType.HLS, caption = "MP4 H264 240p", container = VideoContainer.MP4, height = 240, audioCodec = AudioCodec.AAC, audioBitrate = 48, videoCodec = VideoCodec.H264),
    "93" to Media.Video(streamingType = StreamingType.HLS, caption = "MP4 H264 360p", container = VideoContainer.MP4, height = 360, audioCodec = AudioCodec.AAC, audioBitrate = 128, videoCodec = VideoCodec.H264),
    "94" to Media.Video(streamingType = StreamingType.HLS, caption = "MP4 H264 480p", container = VideoContainer.MP4, height = 480, audioCodec = AudioCodec.AAC, audioBitrate = 128, videoCodec = VideoCodec.H264),
    "95" to Media.Video(streamingType = StreamingType.HLS, caption = "MP4 H264 720p", container = VideoContainer.MP4, height = 720, audioCodec = AudioCodec.AAC, audioBitrate = 256, videoCodec = VideoCodec.H264),
    "96" to Media.Video(streamingType = StreamingType.HLS, caption = "MP4 H264 1080p", container = VideoContainer.MP4, height = 1080, audioCodec = AudioCodec.AAC, audioBitrate = 256, videoCodec = VideoCodec.H264),
    "132" to Media.Video(streamingType = StreamingType.HLS, caption = "MP4 H264 240p", container = VideoContainer.MP4, height = 240, audioCodec = AudioCodec.AAC, audioBitrate = 48, videoCodec = VideoCodec.H264),
    "151" to Media.Video(streamingType = StreamingType.HLS, caption = "MP4 H264 72p", container = VideoContainer.MP4, height = 72, audioCodec = AudioCodec.AAC, audioBitrate = 24, videoCodec = VideoCodec.H264),

    // DASH mp4 video
    "133" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 H264 240p", container = VideoContainer.MP4, height = 240, videoCodec = VideoCodec.H264),
    "134" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 H264 360p", container = VideoContainer.MP4, height = 360, videoCodec = VideoCodec.H264),
    "135" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 H264 480p", container = VideoContainer.MP4, height = 480, videoCodec = VideoCodec.H264),
    "136" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 H264 720p", container = VideoContainer.MP4, height = 720, videoCodec = VideoCodec.H264),
    "137" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 H264 1080p", container = VideoContainer.MP4, height = 1080, videoCodec = VideoCodec.H264),
    "138" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4", container = VideoContainer.MP4, videoCodec = VideoCodec.H264),
    // Height can vary (https://github.com/ytdl-org/youtube-dl/issues/4559)
    "160" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 H264 144p", container = VideoContainer.MP4, height = 144, videoCodec = VideoCodec.H264),
    "212" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 H264 480p", container = VideoContainer.MP4, height = 480, videoCodec = VideoCodec.H264),
    "264" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 H264 1440p", container = VideoContainer.MP4, height = 1440, videoCodec = VideoCodec.H264),
    "298" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 H264 720p@60", container = VideoContainer.MP4, height = 720, videoCodec = VideoCodec.H264, fps = 60),
    "299" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 H264 1080p@60", container = VideoContainer.MP4, height = 1080, videoCodec = VideoCodec.H264, fps = 60),
    "266" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 H264 2160p", container = VideoContainer.MP4, height = 2160, videoCodec = VideoCodec.H264),

    // Dash mp4 audio
    "139" to Media.Audio(streamingType = StreamingType.DASH, caption = "aac@48kbps", container = AudioContainer.M4A, codec = AudioCodec.AAC, bitrate = 48),
    "140" to Media.Audio(streamingType = StreamingType.DASH, caption = "aac@128kbps", container = AudioContainer.M4A, codec = AudioCodec.AAC, bitrate = 128),
    "141" to Media.Audio(streamingType = StreamingType.DASH, caption = "aac@256kbps", container = AudioContainer.M4A, codec = AudioCodec.AAC, bitrate = 256),
    "256" to Media.Audio(streamingType = StreamingType.DASH, caption = "aac@48kbps", container = AudioContainer.M4A, codec = AudioCodec.AAC, bitrate = null),
    "258" to Media.Audio(streamingType = StreamingType.DASH, caption = "aac@48kbps", container = AudioContainer.M4A, codec = AudioCodec.AAC, bitrate = null),
    "325" to Media.Audio(streamingType = StreamingType.DASH, caption = "aac@48kbps", container = AudioContainer.M4A, codec = AudioCodec.DTSE, bitrate = null),
    "328" to Media.Audio(streamingType = StreamingType.DASH, caption = "aac@48kbps", container = AudioContainer.M4A, codec = AudioCodec.EC3, bitrate = null),

    // Dash webm
    "167" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP8 360p", container = VideoContainer.WEBM, height = 360, width = 640, videoCodec = VideoCodec.VP8),
    "168" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP8 480p", container = VideoContainer.WEBM, height = 480, width = 854, videoCodec = VideoCodec.VP8),
    "169" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP8 720p", container = VideoContainer.WEBM, height = 720, width = 1280, videoCodec = VideoCodec.VP8),
    "170" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP8 1080p", container = VideoContainer.WEBM, height = 1080, width = 1920, videoCodec = VideoCodec.VP8),
    "218" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP8 480p", container = VideoContainer.WEBM, height = 480, width = 854, videoCodec = VideoCodec.VP8),
    "219" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP8 480p", container = VideoContainer.WEBM, height = 480, width = 854, videoCodec = VideoCodec.VP8),
    "278" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 144p", container = VideoContainer.WEBM, height = 144, videoCodec = VideoCodec.VP9),
    "242" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 240p", container = VideoContainer.WEBM, height = 240, videoCodec = VideoCodec.VP9),
    "243" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 360p", container = VideoContainer.WEBM, height = 360, videoCodec = VideoCodec.VP9),
    "244" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 480p", container = VideoContainer.WEBM, height = 480, videoCodec = VideoCodec.VP9),
    "245" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 480p", container = VideoContainer.WEBM, height = 480, videoCodec = VideoCodec.VP9),
    "246" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 480p", container = VideoContainer.WEBM, height = 480, videoCodec = VideoCodec.VP9),
    "247" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 720p", container = VideoContainer.WEBM, height = 720, videoCodec = VideoCodec.VP9),
    "248" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 1080p", container = VideoContainer.WEBM, height = 1080, videoCodec = VideoCodec.VP9),
    "271" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 1440p", container = VideoContainer.WEBM, height = 1440, videoCodec = VideoCodec.VP9),
    // itag 272 videos are either 3840x2160 (e.g. RtoitU2A-3E) or 7680x4320 (sLprVF6d7Ug)
    "272" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 2160p", container = VideoContainer.WEBM, height = 2160, videoCodec = VideoCodec.VP9),
    "302" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 720p@60", container = VideoContainer.WEBM, height = 720, videoCodec = VideoCodec.VP9, fps = 60),
    "303" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 1080p@60", container = VideoContainer.WEBM, height = 1080, videoCodec = VideoCodec.VP9, fps = 60),
    "308" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 1440p@60", container = VideoContainer.WEBM, height = 1440, videoCodec = VideoCodec.VP9, fps = 60),
    "313" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 2160p", container = VideoContainer.WEBM, height = 2160, videoCodec = VideoCodec.VP9),
    "315" to Media.Video(streamingType = StreamingType.DASH, caption = "WEBM VP9 2160p@60", container = VideoContainer.WEBM, height = 2160, videoCodec = VideoCodec.VP9, fps = 60),

    // Dash webm audio
    "171" to Media.Audio(streamingType = StreamingType.DASH, caption = "Vorbis@128kbps", container = AudioContainer.WEBM, codec = AudioCodec.VORBIS, bitrate = 128),
    "172" to Media.Audio(streamingType = StreamingType.DASH, caption = "Vorbis@256kbps", container = AudioContainer.WEBM, codec = AudioCodec.VORBIS, bitrate = 256),

    // Dash webm audio with opus inside
    "249" to Media.Audio(streamingType = StreamingType.DASH, caption = "webm+opus@50kbps", container = AudioContainer.WEBM, codec = AudioCodec.OPUS, bitrate = 50),
    "250" to Media.Audio(streamingType = StreamingType.DASH, caption = "webm+opus@70kbps", container = AudioContainer.WEBM, codec = AudioCodec.OPUS, bitrate = 70),
    "251" to Media.Audio(streamingType = StreamingType.DASH, caption = "webm+opus@160kbps", container = AudioContainer.WEBM, codec = AudioCodec.OPUS, bitrate = 160),

    // RTMP (unnamed)
    // '_rtmp': {'protocol': 'rtmp'), unsupported

    // av01 video only formats sometimes served with "unknown" codecs
    "394" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 144p", container = VideoContainer.MP4, height = 144, videoCodec = VideoCodec.AV01),
    "395" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 240p", container = VideoContainer.MP4, height = 240, videoCodec = VideoCodec.AV01),
    "396" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 360p", container = VideoContainer.MP4, height = 360, videoCodec = VideoCodec.AV01),
    "397" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 480p", container = VideoContainer.MP4, height = 480, videoCodec = VideoCodec.AV01),
    "398" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 720p@60", container = VideoContainer.MP4, height = 720, videoCodec = VideoCodec.AV01, fps = 60),
    "399" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 1080p@60", container = VideoContainer.MP4, height = 1080, videoCodec = VideoCodec.AV01, fps = 60),
    "400" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 1440p", container = VideoContainer.MP4, height = 1440, videoCodec = VideoCodec.AV01),
    "401" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 2160p", container = VideoContainer.MP4, height = 2160, videoCodec = VideoCodec.AV01),
    "402" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 4320p", container = VideoContainer.MP4, height = 4320, videoCodec = VideoCodec.AV01),
    "571" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 4320p@60", container = VideoContainer.MP4, height = 4320, videoCodec = VideoCodec.AV01, fps = 60),
    "694" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 144p@60", container = VideoContainer.MP4, height = 144, videoCodec = VideoCodec.AV01, fps = 60),
    "695" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 240p@60", container = VideoContainer.MP4, height = 240, videoCodec = VideoCodec.AV01, fps = 60),
    "696" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 360p@60", container = VideoContainer.MP4, height = 360, videoCodec = VideoCodec.AV01, fps = 60),
    "697" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 480p@60", container = VideoContainer.MP4, height = 480, videoCodec = VideoCodec.AV01, fps = 60),
    "698" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 720p@60", container = VideoContainer.MP4, height = 720, videoCodec = VideoCodec.AV01, fps = 60),
    "699" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 1080p@60", container = VideoContainer.MP4, height = 1080, videoCodec = VideoCodec.AV01, fps = 60),
    "700" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 1440p@60", container = VideoContainer.MP4, height = 1440, videoCodec = VideoCodec.AV01, fps = 60),
    "701" to Media.Video(streamingType = StreamingType.DASH, caption = "MP4 AV01 2160p@60", container = VideoContainer.MP4, height = 2160, videoCodec = VideoCodec.AV01, fps = 60),
)
