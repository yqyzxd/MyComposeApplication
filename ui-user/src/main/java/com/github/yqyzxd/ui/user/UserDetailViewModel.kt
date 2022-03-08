package com.github.yqyzxd.ui.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yqyzxd.common.compose.ObservableLoadingCounter
import com.github.yqyzxd.common.compose.UiMessageManager
import com.github.yqyzxd.data.UserEntry
import com.github.yqyzxd.domain.observers.ObserveUserDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class UserDetailViewModel @Inject constructor(
    savedStateHandle:SavedStateHandle,
    private val observeUserDetail:ObserveUserDetail
):ViewModel() {


    private val loadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    private val userId:String=savedStateHandle.get("userId")!!

    val state= combine(
        observeUserDetail.flow
    ){ args: Array<*> ->
        UserDetailViewState(
            args[0] as UserEntry
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserDetailViewState.Empty
    )


    init {
        observeUserDetail(ObserveUserDetail.Params(userId,true))
    }
}