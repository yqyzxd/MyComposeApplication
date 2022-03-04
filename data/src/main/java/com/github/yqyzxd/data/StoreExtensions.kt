package com.github.yqyzxd.data

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.fresh
import com.dropbox.android.external.store4.get


suspend inline fun <Key:Any,Output:Any> Store<Key, Output>.fetch(
    key: Key,
    forceFresh:Boolean =false
):Output =when{
    forceFresh ->fresh(key)
    else -> get(key)
}