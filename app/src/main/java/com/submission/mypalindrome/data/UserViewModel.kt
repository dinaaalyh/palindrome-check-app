package com.submission.mypalindrome.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.submission.mypalindrome.data.remote.response.DataItem
import com.submission.mypalindrome.data.remote.retrofit.APIConfig
import kotlinx.coroutines.flow.Flow

class UserViewModel : ViewModel() {
    private val repository = UserRepository(APIConfig.getApiService())

    val userList: Flow<PagingData<DataItem>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = { repository.getUserPagingSource(5) }
    ).flow.cachedIn(viewModelScope)
}