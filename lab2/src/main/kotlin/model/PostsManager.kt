package model

interface PostsManager {
    fun getPostsCount(hashtag: String, time: Int): List<Int>
}