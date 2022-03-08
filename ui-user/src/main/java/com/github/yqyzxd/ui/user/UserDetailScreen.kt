package com.github.yqyzxd.ui.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.yqyzxd.common.compose.rememberFlowWithLifecycle
import androidx.compose.runtime.getValue
import coil.compose.rememberImagePainter

@Composable
fun UserDetailScreen() {
    UserDetailScreen(viewModel = viewModel())
}

@Composable
internal fun UserDetailScreen(
    viewModel: UserDetailViewModel
) {
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(null)
    state?.let { state ->

        UserDetailScreen(state)
    }

}

@Composable
internal fun UserDetailScreen(
    viewState: UserDetailViewState
) {
    viewState.userEntry?.let { user->
        Column {
            Image(
                painter = rememberImagePainter(user.avatar),
                contentDescription = null
            )

            Text(
                "${user.uid}"
            )
        }
    }

}