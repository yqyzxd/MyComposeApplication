package com.github.yqyzxd.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: User
 * Author: wind
 * Date: 2022/3/3 5:40 下午
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
@Entity(
    tableName = "user",
    indices = [
        Index(value = ["uid"], unique = true)
    ],
)
data class UserEntry(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    @ColumnInfo val uid:String,
    @ColumnInfo val avatar:String,
    @ColumnInfo override var page: Int
):PaginatedEntry
