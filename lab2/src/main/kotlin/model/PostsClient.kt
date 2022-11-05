package model

interface PostsClient {
    fun getPostsCount(searchRequest: String, time: Int): Int
}