package com.github.yqyzxd.common.compose

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState


fun CombinedLoadStates.appendErrorOrNull(): UiMessage?{
    return (append.takeIf { it is LoadState.Error } as? LoadState.Error)?.let { UiMessage(it.error) }
}

fun CombinedLoadStates.refreshErrorOrNull():UiMessage?{
    return (refresh.takeIf { it is LoadState.Error } as? LoadState.Error)?.let { UiMessage(it.error) }
}