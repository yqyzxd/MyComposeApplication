package com.github.yqyzxd.common.compose

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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

class UiMessageManager{
    private val mutex =Mutex()
    private val _messages = MutableStateFlow(emptyList<UiMessage>())
    val messages:StateFlow<List<UiMessage>> = _messages.asStateFlow()

    suspend fun emitMessage(message: UiMessage){
        mutex.withLock {
            _messages.value=_messages.value+message
        }
    }

    suspend fun clearMessage(id:Long){
        mutex.withLock {
            _messages.value=_messages.value.filterNot { it.id==id }
        }
    }
}