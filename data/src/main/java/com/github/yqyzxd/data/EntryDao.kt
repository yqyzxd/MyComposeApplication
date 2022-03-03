package com.github.yqyzxd.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Transaction
import androidx.room.Update

abstract class EntryDao<E:Entry> {
    @Insert
    abstract suspend fun insert(entry:E):Long

    @Insert
    abstract suspend fun insertAll(entries:List<E>)

    @Update
    abstract suspend fun update(entry: E)

    @Delete
    abstract suspend fun delete(entry: E)

    @Delete
    abstract suspend fun deleteAll()

    @Transaction
    open suspend fun withTransaction(tx:suspend ()->Unit)=tx()


    suspend fun insertOrUpdate(entry: E):Long{
        return if (entry.id==0L){
            insert(entry)
        }else{
            update(entry)
            entry.id
        }
    }

    open suspend fun insertOrUpdate(entries: List<E>){
        entries.forEach {
            insertOrUpdate(it)
        }
    }
}