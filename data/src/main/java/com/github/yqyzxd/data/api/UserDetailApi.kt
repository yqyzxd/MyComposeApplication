package com.github.yqyzxd.data.api

import com.github.yqyzxd.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserDetailApi {
    @GET("user")
    suspend fun user(@Query("page")userId:String): UserResponse
}