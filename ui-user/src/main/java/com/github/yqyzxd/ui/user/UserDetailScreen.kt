package com.github.yqyzxd.ui.user

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserDetailScreen(){
    UserDetailScreen(viewModel = viewModel())
}

internal fun UserDetailScreen(
    viewModel: UserDetailViewModel
){

}