package com.github.yqyzxd.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(){
    HomeScreen(
        viewModel = viewModel()
    )

}

@Composable
internal fun HomeScreen(viewModel: HomeViewModel){
    val lazyPagingItems=rememberFlowWithLifecycle(flow = viewModel.pagedList).collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,

    ) { paddingValues ->

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = lazyPagingItems.loadState.refresh== LoadState.Loading),
            onRefresh = { lazyPagingItems.refresh() },
            indicatorPadding = paddingValues
        ) {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ){

            }
        }

    }

}