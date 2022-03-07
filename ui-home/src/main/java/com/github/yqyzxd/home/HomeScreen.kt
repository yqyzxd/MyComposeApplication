package com.github.yqyzxd.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.github.yqyzxd.data.Entry
import com.github.yqyzxd.data.PaginatedEntry
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
    EntryGrid(lazyPagingItems =  rememberFlowWithLifecycle(flow = viewModel.pagedList).collectAsLazyPagingItems())

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EntryGrid(
    lazyPagingItems: LazyPagingItems<UserEntry>
){
    val scaffoldState = rememberScaffoldState()

    println("HomeScreen lazyPagingItems ${lazyPagingItems.itemCount}")
    println("HomeScreen loadState ${  lazyPagingItems.loadState.refresh== LoadState.Loading}")
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

            LazyVerticalGrid(
                modifier=Modifier.fillMaxSize(),
                cells = GridCells.Fixed(2),
            ){
                items(lazyPagingItems.itemCount){ index->
                    val mod = Modifier
                        .aspectRatio(2 / 3f)
                        .fillMaxWidth()
                    val entry=lazyPagingItems[index]
                    when{
                        entry !=null->{
                            UserCard(entry = entry, modifier = mod)
                        }
                        else ->{
                            PlaceholderUserCard(modifier = mod)
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