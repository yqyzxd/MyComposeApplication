package com.github.yqyzxd.data.api

import com.github.yqyzxd.data.response.HomeUserResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface HomeUserApi {

    @GET("user_city")
    suspend fun users(@Query("page") page:Int,@Query("limit")limit:Int):HomeUserResponse
}