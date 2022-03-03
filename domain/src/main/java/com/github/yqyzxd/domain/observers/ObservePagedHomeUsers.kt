package com.github.yqyzxd.domain.observers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.yqyzxd.data.UserEntry
import com.github.yqyzxd.domain.PaginatedEntryRemoteMediator
import com.github.yqyzxd.domain.PagingInteractor
import kotlinx.coroutines.flow.Flow


class ObservePagedHomeUsers(
    private val userDao:UserDa
) :PagingInteractor<ObservePagedHomeUsers.Params,UserEntry>(){

    override fun createObservable(params: Params): Flow<PagingData<UserEntry>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = PaginatedEntryRemoteMediator{ page ->

            },
            pagingSourceFactory =
        )
    }

    data class Params(
        override val pagingConfig: PagingConfig
    ):PagingInteractor.Parameters<UserEntry>


}