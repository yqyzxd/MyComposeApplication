package com.github.yqyzxd.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntry::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase:RoomDatabase() {
    abstract fun userDao():UserDao

}