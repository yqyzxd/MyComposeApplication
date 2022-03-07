package com.github.yqyzxd.data.di

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.github.yqyzxd.data.UserDao
import com.github.yqyzxd.data.UserEntry
import com.github.yqyzxd.data.api.UserDetailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Singleton


typealias UserDetailStore=Store<String,UserEntry>

@InstallIn(SingletonComponent::class)
@Module(includes = [ApplicationModule::class])
object UserDetailModule {


    @Provides
    @Singleton
    fun provideUserDetailStore(
        userDao: UserDao,
        api: UserDetailApi
    ):UserDetailStore=StoreBuilder.from(
        fetcher = Fetcher.of { userId:String->
            api.user(userId)
        },
        sourceOfTruth = SourceOfTruth.Companion.of(
            reader = { userId->
                userDao.findByUidFlow(userId)
            },
            writer = { userId,response->
                userDao.withTransaction {
                    val user=userDao.findByUid(userId)
                    response.items?.apply {
                        this.id =user?.id?:0
                        userDao.insertOrUpdate(this)
                    }

                }

            },


        )

    ).build()

}