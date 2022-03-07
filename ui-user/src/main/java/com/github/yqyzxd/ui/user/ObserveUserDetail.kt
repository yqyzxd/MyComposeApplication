package com.github.yqyzxd.ui.user

import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.dropbox.android.external.store4.get
import com.github.yqyzxd.data.UserEntry
import com.github.yqyzxd.data.di.UserDetailStore
import com.github.yqyzxd.domain.SubjectInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ObserveUserDetail(
    private val userStore:UserDetailStore
) : SubjectInteractor<ObserveUserDetail.Params, UserEntry>() {

    override fun createObservable(params: Params): Flow<UserEntry> {
       return userStore.stream(StoreRequest.cached(params.userId,refresh = params.refresh))
            .filter { it is StoreResponse.Data }
            .map { it.requireData() }
            .flowOn(Dispatchers.Default)
    }


    data class Params(val userId: String,val refresh:Boolean=false)


}