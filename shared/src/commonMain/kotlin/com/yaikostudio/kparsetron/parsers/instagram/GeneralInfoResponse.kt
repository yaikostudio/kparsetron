package com.yaikostudio.kparsetron.parsers.instagram

import com.yaikostudio.kparsetron.entities.media.MediaThumbnail
import com.yaikostudio.kparsetron.entities.media.Size
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GeneralInfoResponse(
    val data: Data,
) {
    @Serializable
    data class Data(
        @SerialName("xdt_shortcode_media") val xdtShortcodeMedia: ShortcodeMedia,
    ) {
        @Serializable
        data class ShortcodeMedia(
            @SerialName("edge_media_to_caption") val edgeMediaToCaption: EdgeMediaToCaption,
            @SerialName("video_url") val videoUrl: String? = null,
            @SerialName("video_view_count") val videoViewCount: Long? = null,
            @SerialName("video_duration") val videoDuration: Double? = null,
            @SerialName("dimensions") val dimensions: Dimensions,
            @SerialName("display_resources") val displayResources: List<DisplayResource>,
        ) {
            @Serializable
            data class EdgeMediaToCaption(
                val edges: List<Edge>,
            ) {
                @Serializable
                data class Edge(
                    val node: Node,
                ) {
                    @Serializable
                    data class Node(
                        val text: String,
                    )
                }
            }

            @Serializable
            data class Dimensions(
                val height: Int,
                val width: Int,
            )

            @Serializable
            data class DisplayResource(
                @SerialName("src") val src: String,
                @SerialName("config_width") val configWidth: Int,
                @SerialName("config_height") val configHeight: Int,
            ) {
                fun toMediaThumbnail() = MediaThumbnail(
                    type = MediaThumbnail.Type.IMAGE,
                    url = src,
                    size = Size(
                        width = configWidth,
                        height = configHeight,
                    ),
                )
            }
        }
    }
}
