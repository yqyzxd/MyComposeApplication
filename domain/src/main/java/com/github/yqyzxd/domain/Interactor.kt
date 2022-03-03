package com.github.yqyzxd.domain

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit


abstract class Interactor<in P>{

    operator fun invoke(params:P,timeout:Long= DEFAULT_TIMEOUT): Flow<InvokeStatus> = flow<InvokeStatus> {
        withTimeout(timeout){
            emit(InvokeStarted)
            doWork(params)
            emit(InvokeSuccess)
        }

    }.catch { t -> emit(InvokeError(t)) }

    suspend fun executeSync(params:P)=doWork(params)

    protected abstract suspend fun doWork(params:P)

    companion object{
        private val DEFAULT_TIMEOUT=TimeUnit.MINUTES.toMillis(5)
    }
}

abstract class SubjectInteractor<P:Any,T>{
    private val paramState = MutableSharedFlow<P>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val flow:Flow<T> = paramState
        .distinctUntilChanged()
        .flatMapLatest { createObservable(it) }
        .distinctUntilChanged()

    protected abstract fun createObservable(params:P):Flow<T>

    operator fun invoke(params:P){
        paramState.tryEmit(params)
    }
}

abstract class PagingInteractor<P:PagingInteractor.Parameters<T>,T:Any> : SubjectInteractor<P,PagingData<T>>(){

    interface Parameters<T:Any>{
        val pagingConfig:PagingConfig
    }
}