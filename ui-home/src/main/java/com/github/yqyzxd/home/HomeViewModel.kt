package com.github.yqyzxd.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.yqyzxd.data.UserEntry
import com.github.yqyzxd.domain.observers.ObservePagedHomeUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(pagingInteractor:ObservePagedHomeUsers) : ViewModel() {

    val pagedList:Flow<PagingData<UserEntry>> = pagingInteractor.flow.cachedIn(viewModelScope)

    init {
        pagingInteractor(ObservePagedHomeUsers.Params(PAGING_CONFIG))

    }

    companion object{
        val PAGING_CONFIG = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10
        )
    }
}