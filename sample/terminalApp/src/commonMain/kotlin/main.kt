import com.yaikostudio.kparsetron.Kparsetron
import com.yaikostudio.kparsetron.entities.ParsedVideoDetail
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val result = Kparsetron().parse("youtu.be/pmoYP_QvGsM")
        println(result)

        if (null == result) {
            println("Failed to parse video.")
        } else if (result is ParsedVideoDetail) {
            println("Title: ${result.data.title}")
            println("Duration: ${result.data.duration}")
            println("View Count: ${result.data.viewCount}")
            result.data.thumbnails.forEach {
                println("Thumbnail: ${it.url} (${it.size?.width}x${it.size?.height})")
            }
            result.data.parts.forEach { part ->
                println("Part title: ${part.title}")
                part.videoAlternatives.forEach { alternative ->
                    println("Video: ${alternative.media}")
                }
                part.audioAlternatives.forEach { alternative ->
                    println("Audio: ${alternative.media}")
                }
            }
            result.data.relatedMedia.forEach {
                println("Related: $it")
            }
        } else {
            throw UnsupportedOperationException("Unsupported result type: ${result::class}")
        }
    }
}
