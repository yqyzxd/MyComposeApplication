package com.github.yqyzxd.home

import java.util.*


data class UiMessage(
    val message: String,
    val id: Long = UUID.randomUUID().mostSignificantBits
)

fun UiMessage(
    t: Throwable,
    id: Long = UUID.randomUUID().mostSignificantBits
): UiMessage = UiMessage(
    message = t.message ?: "Error occurred:$t",
    id = id
)