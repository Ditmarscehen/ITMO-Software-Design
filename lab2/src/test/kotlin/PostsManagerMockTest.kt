import model.PostsClient
import model.PostsManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import vk.VkPostsManager


class PostsManagerMockTest {
    private lateinit var postClient: PostsClient
    private lateinit var postsManager: PostsManager

    companion object {
        private const val DEFAULT_HASHTAG = "#top"
        private const val DEFAULT_TIME = 1
    }

    @Before
    fun init() {
        postClient = mock()
        postsManager = VkPostsManager(postClient)
    }

    @Test
    fun getPostsCount() {
        val expectedValue = 1

        whenever(postClient.getPostsCount(DEFAULT_HASHTAG, DEFAULT_TIME, DEFAULT_TIME - 1)).thenReturn(expectedValue)
        assertThat(postsManager.getPostsCount(DEFAULT_HASHTAG, DEFAULT_TIME)).isEqualTo(listOf(expectedValue))
    }

    @Test
    fun getSeveralPostsCount() {
        val expectedValues = listOf(1, 2, 3, 4)
        whenever(postClient.getPostsCount(DEFAULT_HASHTAG, 4, 3)).thenReturn(1)
        whenever(postClient.getPostsCount(DEFAULT_HASHTAG, 3, 2)).thenReturn(2)
        whenever(postClient.getPostsCount(DEFAULT_HASHTAG, 2, 1)).thenReturn(3)
        whenever(postClient.getPostsCount(DEFAULT_HASHTAG, 1, 0)).thenReturn(4)
        assertThat(postsManager.getPostsCount(DEFAULT_HASHTAG, 4)).isEqualTo(expectedValues)
    }

    @Test
    fun checkIllegalHashTag() {
        val hashTag = "not-hashtag"

        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy {
            postsManager.getPostsCount(hashTag, DEFAULT_TIME)
        }
    }

    @Test
    fun checkIllegalTime() {
        val time = -1

        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy {
            postsManager.getPostsCount(DEFAULT_HASHTAG, time)
        }
    }
}