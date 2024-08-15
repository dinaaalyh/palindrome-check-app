package com.submission.mypalindrome.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.submission.mypalindrome.data.remote.response.DataItem
import com.submission.mypalindrome.data.remote.retrofit.APIService

class UserPagingSource(
    private val service: APIService, private val itemsPerPage: Int
) : PagingSource<Int, DataItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        val currentPage = params.key ?: 1
        return try {
            val apiResponse = service.getUsers(page = currentPage, itemsPerPage = itemsPerPage)
            val userList = apiResponse.data.map { dataItem ->
                DataItem(
                    id = dataItem.id,
                    firstName = dataItem.firstName,
                    lastName = dataItem.lastName,
                    email = dataItem.email,
                    avatar = dataItem.avatar
                )
            }

            LoadResult.Page(
                data = userList,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (userList.size < itemsPerPage) null else currentPage + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            val closestPage = state.closestPageToPosition(anchorPos)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }
}