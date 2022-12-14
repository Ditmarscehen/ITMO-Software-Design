package vk

import model.PostsClient
import model.PostsManager

class VkPostsManager(private val postsClient: PostsClient) : PostsManager {
    override fun getPostsCount(hashtag: String, time: Int): List<Int> {
        validateArgs(hashtag, time)

        try {
            return (time downTo 1)
                .map { postsClient.getPostsCount(hashtag, it, it - 1) }

        } catch (e: Exception) {
            throw RuntimeException("Failed to get posts count: ${e.message}")
        }

    }

    private fun validateArgs(hashTag: String, time: Int) {
        require(hashTag.startsWith("#")) { "Hashtag must start with #" }
        require(time in (1..24)) { "Time must be in 1..24" }
    }
}