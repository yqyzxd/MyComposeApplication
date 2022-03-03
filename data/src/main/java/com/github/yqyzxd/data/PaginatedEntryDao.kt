package com.github.yqyzxd.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction

abstract class PaginatedEntryDao<E:PaginatedEntry> :EntryDao<E>(){

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insert(entry: E): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(entries: List<E>)

    abstract suspend fun deletePage(page:Int)
    abstract suspend fun getLastPage():Int?


    @Transaction
    open suspend fun updatePage(page: Int,entries: List<E>){
        deletePage(page)
        insertAll(entries)
    }
}