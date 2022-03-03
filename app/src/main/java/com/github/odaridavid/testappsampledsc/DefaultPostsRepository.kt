package com.github.odaridavid.testappsampledsc

interface PostsRepository{
   suspend fun getPosts():List<Post>
}

class DefaultPostsRepository(private val jsonPlaceHolderApi: JsonPlaceHolderApi):PostsRepository {

    override suspend fun getPosts():List<Post>{
       return jsonPlaceHolderApi.getPosts().body()?.map { response ->
            Post(
                id = response.id,
                body = response.body,
                title = response.title,
                userId = response.userId
            )
        } ?: emptyList()
    }

}
