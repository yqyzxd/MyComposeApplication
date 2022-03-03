package com.github.yqyzxd.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao:PaginatedEntryDao<UserEntry>() {

    @Transaction
    @Query("SELECT * FROM user WHERE page = :page")
    abstract fun entriesObservable(page: Int):Flow<List<UserEntry>>

    @Query("SELECT * FROM user ORDER BY page")
    abstract fun entriesPagingSource(): PagingSource<Int, UserEntry>

    @Query("DELETE FROM user")
    abstract override suspend fun deleteAll()

    @Query("DELETE FROM user WHERE page = :page")
    abstract override suspend fun deletePage(page: Int)

    @Query("SELECT MAX(page) FROM user")
    abstract override suspend fun getLastPage(): Int?
}