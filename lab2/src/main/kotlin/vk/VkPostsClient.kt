package vk

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import model.PostsClient
import utils.TimeUtils.Companion.calculateStartTime


class VkPostsClient(private val host: String, private val port: Int) : PostsClient {
    private val client = HttpClient {
        install(Resources)
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        defaultRequest {
            host = this@VkPostsClient.host
            port = this@VkPostsClient.port
            url { protocol = URLProtocol.HTTP }
        }
    }

    override fun getPostsCount(searchRequest: String, time: Int): Int {
        val startTime = calculateStartTime(time)
        
        val postsCount: Int =
            runBlocking {
                val response = client.get(PostsRequest(searchRequest, startTime))
                val postsResponse = Json.decodeFromString(PostsResponse.serializer(), response.bodyAsText())
                postsResponse.totalCount
            }

        return postsCount
    }
}