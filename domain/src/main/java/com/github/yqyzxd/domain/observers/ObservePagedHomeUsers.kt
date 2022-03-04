package com.github.yqyzxd.domain.observers

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.yqyzxd.data.UserDao
import com.github.yqyzxd.data.UserEntry
import com.github.yqyzxd.domain.PaginatedEntryRemoteMediator
import com.github.yqyzxd.domain.PagingInteractor
import com.github.yqyzxd.domain.interactors.HomeUsersInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ObservePagedHomeUsers @Inject constructor(
    private val userDao: UserDao,
    private val homeUsersInteractor: HomeUsersInteractor
) :PagingInteractor<ObservePagedHomeUsers.Params,UserEntry>(){


    override fun createObservable(params: Params): Flow<PagingData<UserEntry>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = PaginatedEntryRemoteMediator{ page ->
                homeUsersInteractor.executeSync(HomeUsersInteractor.Params(page = page, forceRefresh = true))
            },
            pagingSourceFactory = userDao::entriesPagingSource
        ).flow
    }

    data class Params(
        override val pagingConfig: PagingConfig
    ):Parameters<UserEntry>


}