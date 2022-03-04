package com.github.yqyzxd.data.di

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.github.yqyzxd.data.UserDao
import com.github.yqyzxd.data.UserEntry
import com.github.yqyzxd.data.api.HomeUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.flow.map
import javax.inject.Singleton


typealias HomeUserStore = Store<Int, List<UserEntry>>

@InstallIn(ApplicationComponent::class)
@Module
class HomeUserModule {

    @Singleton
    @Provides
    fun provideHomeUserStore(
        api: HomeUserApi,
        userDao: UserDao
    ): HomeUserStore {

        return StoreBuilder.from(
            fetcher =   Fetcher.of { page:Int->
                api.users(page,20)
            },
            sourceOfTruth = SourceOfTruth.of(
                reader = { page->
                    userDao.entriesObservable(page).map {  entries->
                        when{
                            entries.isEmpty() -> null
                            else -> entries
                        }
                    }
                },
                writer = { page, response->
                    userDao.withTransaction {
                        var users= response.items?.users?: mutableListOf()
                        if (page == 0){
                            userDao.deleteAll()
                            userDao.insertAll(users)
                        }else{
                            userDao.updatePage(page,users)
                        }

                    }
                },
                delete = userDao::deletePage,
                deleteAll = userDao::deleteAll
            )
        ).build()


    }
}

