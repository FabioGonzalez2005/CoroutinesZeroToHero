package org.iesharia

import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {
    @GET("comments")
    suspend fun getComments(): Response<List<Comment>>
}
