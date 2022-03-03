package com.github.odaridavid.testappsampledsc

import retrofit2.Response
import retrofit2.http.GET

interface JsonPlaceHolderApi {

    @GET("/posts")
    suspend fun getPosts(): Response<List<PostsResponse>>
}
