package com.github.yqyzxd.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yqyzxd.common.compose.ObservableLoadingCounter
import com.github.yqyzxd.common.compose.UiMessageManager
import com.github.yqyzxd.data.UserEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class UserDetailViewModel @Inject constructor(
    private var observeUserDetail:ObserveUserDetail
):ViewModel() {


    private val loadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()


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


}