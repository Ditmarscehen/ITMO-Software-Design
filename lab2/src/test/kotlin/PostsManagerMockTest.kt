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
        private const val DEFAULT_TIME = 10
    }

    @Before
    fun init() {
        postClient = mock()
        postsManager = VkPostsManager(postClient)
    }

    @Test
    fun getPostsCount() {
        val expectedValue = 1

        whenever(postClient.getPostsCount(DEFAULT_HASHTAG, DEFAULT_TIME)).thenReturn(expectedValue)
        assertThat(postsManager.getPostsCount(DEFAULT_HASHTAG, DEFAULT_TIME)).isEqualTo(expectedValue)
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