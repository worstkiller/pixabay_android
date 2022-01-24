package com.vikas.pixabayandroid.repo

import android.content.Context
import android.content.res.Configuration
import androidx.paging.*
import androidx.paging.rxjava2.observable
import com.vikas.pixabayandroid.api.PixabayService
import com.vikas.pixabayandroid.persistence.AppDatabase
import com.vikas.pixabayandroid.persistence.PixabayModel
import com.vikas.pixabayandroid.repo.paging.PixabayImageDBPagingSource
import com.vikas.pixabayandroid.repo.paging.PixabayImagePagingSource
import com.vikas.pixabayandroid.repo.paging.PixabayMediator
import com.vikas.pixabayandroid.utils.PixabayUtils
import io.reactivex.Observable
import javax.inject.Inject

@ExperimentalPagingApi
class PixabayRepository @Inject constructor(
    val apiService: PixabayService,
    val pixabayDao: AppDatabase,
    val context: Context
) {

    companion object {

        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

    }

    /**
     * loads content for landscape and portrait differently, local DB caching supported
     * if orientation is landscape and content is present than it will be trying to return @see[PagingSource] from room DB else
     * it will make an api call to fetch the data.
     *
     * in case of portrait we are loading data from the internet and paging data using local room DB as a mediator
     * @see[PixabayMediator]
     */
    fun getImagesObservable(
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): Observable<PagingData<PixabayModel>> {
        return if (PixabayUtils.getOrientation(context) == Configuration.ORIENTATION_LANDSCAPE) {
            Pager(
                config = pagingConfig,
                pagingSourceFactory = { PixabayImageDBPagingSource(pixabayDao, apiService) }
            ).observable
        } else {
            Pager(
                config = pagingConfig,
                pagingSourceFactory = { PixabayImagePagingSource(apiService) },
                remoteMediator = PixabayMediator(apiService, pixabayDao)
            ).observable
        }
    }

    /**
     * let's define page size, page size is the only required param, rest is optional
     */
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

}