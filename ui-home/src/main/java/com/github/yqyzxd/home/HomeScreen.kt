package com.github.yqyzxd.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.github.yqyzxd.data.UserEntry
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
    val lazyPagingItems = rememberFlowWithLifecycle(flow = viewModel.pagedList).collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()

    lazyPagingItems.loadState.appendErrorOrNull()?.let { message->
        LaunchedEffect(message){
            scaffoldState.snackbarHostState.showSnackbar(message = message.message)
        }

    }
    lazyPagingItems.loadState.refreshErrorOrNull()?.let { message->
        LaunchedEffect(message){
            scaffoldState.snackbarHostState.showSnackbar(message = message.message)
        }
    }

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
                itemsInGrid(
                    lazyPagingItems = lazyPagingItems,
                    columns = 2,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalItemPadding = 8.dp,
                    horizontalItemPadding = 8.dp
                ){ entry->
                   when{
                       entry !=null->{
                           UserCard(entry = entry)
                       }
                       else ->{
                           PlaceholderUserCard()
                       }
                   }
                }
            }
        }

    }

}


@Composable
fun UserCard(modifier: Modifier=Modifier,entry:UserEntry){
    Card(modifier = modifier) {
        Box{
            Image(
                painter = rememberImagePainter(entry.avatar),
                contentDescription =null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun PlaceholderUserCard(modifier: Modifier=Modifier){
    Card(modifier = modifier) {
        Box{
            Image(
                painter = rememberImagePainter("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Ffbf18a5314f750da671711dfb176cf8791fbc687153d-g7YSBF_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1649040929&t=3bfdf600b70a7b143384adaf02ba2792"),
                contentDescription =null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}