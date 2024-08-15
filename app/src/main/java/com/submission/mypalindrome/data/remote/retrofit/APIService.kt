package com.submission.mypalindrome.data.remote.retrofit

import com.submission.mypalindrome.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("api/users")
    suspend fun getUsers(
        @Query("page") page: Int = 1,
        @Query("per_page") itemsPerPage: Int = 5
    ): UserResponse
}