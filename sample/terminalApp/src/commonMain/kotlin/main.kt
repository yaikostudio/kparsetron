import com.yaikostudio.kparsetron.Kparsetron
import com.yaikostudio.kparsetron.entities.ParsedVideoDetail
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val result = Kparsetron().parse("https://www.instagram.com/sviatoslav.vakarchuk/reel/DG8cE9dt2MI/")
        println(result)

        if (null == result) {
            println("Failed to parse video.")
        } else if (result is ParsedVideoDetail) {
            println("Title: ${result.data.title}")
            println("Owner: ${result.data.owner}")
            println("Duration: ${result.data.duration}")
            println("View Count: ${result.data.viewCount}")
            result.data.thumbnails.forEach {
                println("Thumbnail: ${it.url} (${it.size?.width}x${it.size?.height})")
            }
            result.data.parts.forEach { part ->
                println("Part title: ${part.title}")
                part.videoAlternatives.forEach { alternative ->
                    println("Video: $alternative")
                }
                part.audioAlternatives.forEach { alternative ->
                    println("Audio: $alternative")
                }
            }
            result.data.relatedMedia.forEach {
                println("Related: $it")
            }
            result.data.upNext.forEach {
                println("Up Next: $it")
            }
            result.data.comments.forEach {
                println("Comment: $it")
            }
        } else {
            throw UnsupportedOperationException("Unsupported result type: ${result::class}")
        }
    }
}
