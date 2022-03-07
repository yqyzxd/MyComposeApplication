package com.github.yqyzxd.ui.user

import com.github.yqyzxd.data.UserEntry

internal data class UserDetailViewState(
    val userEntry: UserEntry?=null
){
    companion object{
        val Empty=UserDetailViewState()
    }
}