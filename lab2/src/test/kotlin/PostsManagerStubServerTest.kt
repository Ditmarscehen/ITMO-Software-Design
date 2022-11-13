@file:Suppress("SameParameterValue")

import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Action.stringContent
import com.xebialabs.restito.semantics.Condition.method
import com.xebialabs.restito.semantics.Condition.startsWithUri
import com.xebialabs.restito.server.StubServer
import kotlinx.serialization.json.Json
import org.glassfish.grizzly.http.Method
import org.junit.Assert
import org.junit.Test
import vk.PostsResponse
import vk.VkPostsClient

class PostsManagerStubServerTest {
    companion object {
        private const val PORT = 8888
        private const val HOST = "0.0.0.0"
        private val jsonFormat = Json {
            prettyPrint = true
        }
    }

    private val vkPostsClient = VkPostsClient(HOST, PORT)

    @Test
    fun getPostsCount() {
        val response = PostsResponse(1)
        val responseJson = jsonFormat.encodeToString(PostsResponse.serializer(), response)

        withStubServer(PORT) { s ->
            whenHttp(s)
                .match(method(Method.GET), startsWithUri("/newsfeed.search"))
                .then(stringContent(responseJson))

            val result = vkPostsClient.getPostsCount("top", 11, 12)
            Assert.assertEquals(response.totalCount, result)
        }
    }

    private fun withStubServer(port: Int, callback: (StubServer) -> Unit) {
        var stubServer: StubServer? = null
        try {
            stubServer = StubServer(port).run()
            callback(stubServer)
        } finally {
            stubServer?.stop()
        }
    }
}