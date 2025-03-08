import com.yaikostudio.kparsetron.data.network.Downloader
import io.ktor.http.parseUrl
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val result = Downloader().download(parseUrl("https://www.youtube.com/watch?v=pmoYP_QvGsM")!!, null)
        println(result)
    }
}
