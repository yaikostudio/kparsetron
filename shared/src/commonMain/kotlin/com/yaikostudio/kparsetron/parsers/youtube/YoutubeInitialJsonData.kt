package com.yaikostudio.kparsetron.parsers.youtube

import kotlinx.serialization.Serializable

@Serializable
data class YoutubeInitialJsonData(
    val contents: Contents,
) {
    @Serializable
    data class Contents(
        val twoColumnWatchNextResults: TwoColumnWatchNextResults,
    ) {
        @Serializable
        data class TwoColumnWatchNextResults(
            val secondaryResults: SecondaryResults,
        ) {
            @Serializable
            data class SecondaryResults(
                val secondaryResults: SecondaryResultInner,
            ) {
                @Serializable
                data class SecondaryResultInner(
                    val results: List<Content>,
                ) {
                    @Serializable
                    data class Content(
                        val compactVideoRenderer: CompactVideoRenderer? = null,
                    ) {
                        @Serializable
                        data class CompactVideoRenderer(
                            val videoId: String,
                            val thumbnail: YoutubeThumbnail,
                            val title: Title,
                        ) {
                            @Serializable
                            data class Title(
                                val simpleText: String,
                            )
                        }
                    }
                }
            }
        }
    }
}
