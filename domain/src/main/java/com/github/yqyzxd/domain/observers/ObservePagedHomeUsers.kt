package com.github.yqyzxd.domain.observers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.yqyzxd.data.User
import com.github.yqyzxd.domain.PagingInteractor
import kotlinx.coroutines.flow.Flow


class ObservePagedHomeUsers :PagingInteractor<ObservePagedHomeUsers.Params,User>(){

    override fun createObservable(params: Params): Flow<PagingData<User>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator =
        )
    }

    data class Params(
        override val pagingConfig: PagingConfig
    ):PagingInteractor.Parameters<User>


}