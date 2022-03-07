package com.github.yqyzxd.data.di

import android.content.Context
import androidx.room.Room
import com.github.yqyzxd.data.AppRoomDatabase
import com.github.yqyzxd.data.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Provides
    fun provideRetrofit():Retrofit{


        //网络请求日志
        val loggingInterceptor = HttpLoggingInterceptor()
        val level = HttpLoggingInterceptor.Level.BODY

        loggingInterceptor.level = level


        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder
            .addInterceptor(loggingInterceptor)
            .addInterceptor(HeaderInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS) //设置连接超时时间
            .readTimeout(20, TimeUnit.SECONDS) //设置读取超时时间

        val client = httpClientBuilder.build()

        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://test.marryu520.cn/")
            .client(client)
            .build()
    }
    @Provides
    fun provideDatabase(
        @ApplicationContext context:Context
    ): AppRoomDatabase {
        val builder=Room.databaseBuilder(context,AppRoomDatabase::class.java,"room.db")
            .fallbackToDestructiveMigration()

        return builder.build()
    }
    @Provides
    fun provideUserDao(db:AppRoomDatabase) =db.userDao()
}