package com.github.yqyzxd.domain.interactors

import com.github.yqyzxd.data.UserDao
import com.github.yqyzxd.data.di.HomeUserStore
import com.github.yqyzxd.data.fetch
import com.github.yqyzxd.domain.Interactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class HomeUsersInteractor @Inject constructor(
    private val userDao: UserDao,
    private val homeUserStore: HomeUserStore
) : Interactor<HomeUsersInteractor.Params>() {

    override suspend fun doWork(params: Params) {
        withContext(Dispatchers.IO) {
            val page = when {
                params.page >= 0 -> params.page
                params.page == Page.NEXT_PAGE -> {
                    val lastPage = userDao.getLastPage()
                    if (lastPage != null) lastPage + 1 else 0
                }
                else -> 0
            }
            homeUserStore.fetch(page, forceFresh = params.forceRefresh)


        }
    }

    data class Params(val page: Int, val forceRefresh: Boolean = false)

    object Page {
        const val NEXT_PAGE = -1
        const val REFRESH = -2
    }
}