package com.github.odaridavid.testappsampledsc

import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

@RunWith(JUnitParamsRunner::class)
class PostRepositoryTest {

    @Suppress("unused")
    private fun paramsForLoadingPosts() = arrayOf(
        arrayOf(
            Response.success(
                listOf(
                    PostsResponse(
                        id = 1,
                        userId = 2,
                        body = "",
                        title = ""
                    )
                )
            ),
            listOf<Post>(
                Post(
                    id = 1,
                    userId = 2,
                    body = "",
                    title = ""
                )
            )
        ),
        arrayOf(
            Response.error<List<PostsResponse>>(404, ResponseBody.create(null, "")),
            emptyList<Post>()
        )
    )

    @Test
    @Parameters(method = "paramsForLoadingPosts")
    fun `when the posts are loaded, then the success result are returned`(
        postsResponse: Response<List<PostsResponse>>,
        expectedPosts: List<Post>
    ) = runTest {
        // given objects under tests
        val jsonPlaceHolderApiTesting = mock<JsonPlaceHolderApi>().apply {
            whenever(getPosts()).thenReturn(postsResponse)
        }
        val postsRepository = DefaultPostsRepository(jsonPlaceHolderApi = jsonPlaceHolderApiTesting)

        // when we load posts
        val posts = postsRepository.getPosts()

        // then the expected result is returned
        assert(posts == expectedPosts) {
            println("Expected : $expectedPosts")
            println("Actual : $posts")
        }
    }

}
