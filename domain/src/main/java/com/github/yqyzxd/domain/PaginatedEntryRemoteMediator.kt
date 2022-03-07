package com.github.yqyzxd.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.github.yqyzxd.data.PaginatedEntry

@OptIn(ExperimentalPagingApi::class)
class PaginatedEntryRemoteMediator<E>(
    private val fetch:suspend (page:Int)-> Unit
) :RemoteMediator<Int,E>() where E:PaginatedEntry{



    override suspend fun load(loadType: LoadType, state: PagingState<Int, E>): MediatorResult {
        val nextPage = when(loadType){
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem=state.lastItemOrNull()?: return MediatorResult.Success(endOfPaginationReached = true)
                lastItem.page+1
            }
        }

        return try {
            fetch(nextPage)
            println("PaginatedEntryRemoteMediator MediatorResult.Success")
            MediatorResult.Success(endOfPaginationReached = false)
        }catch (t:Throwable){
            MediatorResult.Error(t)
        }
    }
}