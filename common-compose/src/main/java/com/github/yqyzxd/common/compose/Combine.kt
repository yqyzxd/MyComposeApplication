package com.github.yqyzxd.common.compose

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine



fun <T1, T2, T3, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    transform: suspend (T1, T2, T3) -> R
): Flow<R> = combine(flow,flow2,flow3){ args:Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3
    )

}