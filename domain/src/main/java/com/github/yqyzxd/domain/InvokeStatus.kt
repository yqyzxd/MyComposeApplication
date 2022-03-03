package com.github.yqyzxd.domain

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: InvokeStatus
 * Author: wind
 * Date: 2022/3/3 3:14 下午
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
sealed class InvokeStatus
object InvokeStarted : InvokeStatus()
object InvokeSuccess : InvokeStatus()
data class InvokeError(val throwable: Throwable) : InvokeStatus()
