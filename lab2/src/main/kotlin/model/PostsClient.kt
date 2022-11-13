package model

interface PostsClient {
    fun getPostsCount(searchRequest: String, startTime: Int, endTime: Int): Int
}