package vk

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Resource("/newsfeed.search")
class PostsRequest(
    @SerialName("q") val searchRequest: String,
    @SerialName("start_time") val startTime: String,
    @SerialName("end_time") val endTime: String
)