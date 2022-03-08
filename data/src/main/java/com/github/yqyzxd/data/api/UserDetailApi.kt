package com.github.yqyzxd.data.api

import com.github.yqyzxd.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserDetailApi {
    @GET("user/detail")
    suspend fun user(@Query("uid")userId:String): UserResponse
}