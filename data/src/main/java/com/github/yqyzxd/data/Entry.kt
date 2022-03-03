package com.github.yqyzxd.data

interface Entry{
    val id:Long
}
interface PaginatedEntry:Entry{
    val page:Int
}