package com.vikas.pixabayandroid.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import com.vikas.pixabayandroid.api.PixabayService
import com.vikas.pixabayandroid.persistence.AppDatabase
import com.vikas.pixabayandroid.persistence.PixabayModel
import com.vikas.pixabayandroid.repo.paging.PixabayImagePagingSource
import com.vikas.pixabayandroid.repo.paging.PixabayMediator
import io.reactivex.Observable
import javax.inject.Inject

@ExperimentalPagingApi
class PixabayRepository @Inject constructor(
    val apiService: PixabayService,
    val pixabayDao: AppDatabase
) {

    companion object {

        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

    }

    fun getImagesObservable(
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): Observable<PagingData<PixabayModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { PixabayImagePagingSource(apiService) },
            remoteMediator = PixabayMediator(apiService, pixabayDao)
        ).observable
    }

    /**
     * let's define page size, page size is the only required param, rest is optional
     */
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

}