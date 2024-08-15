package com.submission.mypalindrome.data

import com.submission.mypalindrome.data.remote.retrofit.APIService

class UserRepository(private val apiService: APIService) {
    fun getUserPagingSource(itemsPerPage: Int) = UserPagingSource(apiService, itemsPerPage)
}