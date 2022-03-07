package com.github.yqyzxd.common.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems


fun LazyListScope.itemSpacer(height: Dp) {
    item {
        Spacer(
            modifier = Modifier
                .height(height = height)
                .fillParentMaxWidth()
        )
    }
}

fun <T:Any> LazyListScope.itemsInGrid(
    lazyPagingItems: LazyPagingItems<T>,
    columns: Int,
    contentPadding: PaddingValues = PaddingValues(),
    horizontalItemPadding: Dp = 0.dp,
    verticalItemPadding: Dp = 0.dp,
    itemContent: @Composable LazyItemScope.(T?) -> Unit
){
    itemsInGrid(
        size = lazyPagingItems.itemCount,
        columns = columns,
        contentPadding = contentPadding,
        horizontalItemPadding = horizontalItemPadding,
        verticalItemPadding = verticalItemPadding,
        itemProvider = {index:Int->lazyPagingItems[index]},
        itemContent = itemContent
    )
}

fun <T> LazyListScope.itemsInGrid(
    items: List<T>,
    columns: Int,
    contentPadding: PaddingValues = PaddingValues(),
    horizontalItemPadding: Dp = 0.dp,
    verticalItemPadding: Dp = 0.dp,
    itemContent: @Composable LazyItemScope.(T) -> Unit
) {

    itemsInGrid(
        size = items.size,
        columns = columns,
        contentPadding = contentPadding,
        horizontalItemPadding = horizontalItemPadding,
        verticalItemPadding = verticalItemPadding,
        itemProvider = { index: Int -> items[index] },
        itemContent = itemContent
    )

}

fun <T> LazyListScope.itemsInGrid(
    size: Int,
    columns: Int,
    contentPadding: PaddingValues = PaddingValues(),
    horizontalItemPadding: Dp = 0.dp,
    verticalItemPadding: Dp = 0.dp,
    itemProvider: (index: Int) -> T,
    itemContent: @Composable LazyItemScope.(T) -> Unit
) {
    val rows = when {
        size % columns == 0 -> size / columns
        else -> (size / columns) + 1
    }

    for (row in 0 until rows) {
        if (row == 0) itemSpacer(contentPadding.calculateTopPadding())

        item {
            val layoutDirection = LocalLayoutDirection.current

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = contentPadding.calculateStartPadding(layoutDirection),
                        end = contentPadding.calculateEndPadding(layoutDirection)
                    )
            ) {
                for (column in 0 until columns) {

                    Box(modifier = Modifier.weight(1f)) {
                        val index = (row * column) + column
                        if (index < size) {
                            itemContent(itemProvider(index))
                        }
                    }
                    if (column < columns - 1) {
                        Spacer(modifier = Modifier.width(horizontalItemPadding))
                    }
                }
            }
        }

        if (row < rows - 1) {
            itemSpacer(verticalItemPadding)
        } else {
            itemSpacer(contentPadding.calculateBottomPadding())
        }
    }
}