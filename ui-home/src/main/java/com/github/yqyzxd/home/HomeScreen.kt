package com.github.yqyzxd.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(){
    HomeScreen(
        viewModel = viewModel()
    )

}

fun HomeScreen(viewModel: HomeViewModel){


}