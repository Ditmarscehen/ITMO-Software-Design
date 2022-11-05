package vk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsResponse(@SerialName("total_count") val totalCount: Int)