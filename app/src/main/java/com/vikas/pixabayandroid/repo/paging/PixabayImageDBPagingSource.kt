package com.vikas.pixabayandroid.repo.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vikas.pixabayandroid.BuildConfig
import com.vikas.pixabayandroid.api.PixabayService
import com.vikas.pixabayandroid.persistence.AppDatabase
import com.vikas.pixabayandroid.persistence.PixabayModel
import com.vikas.pixabayandroid.repo.PixabayRepository
import com.vikas.pixabayandroid.repo.PixabayRepository.Companion.DEFAULT_PAGE_INDEX
import com.vikas.pixabayandroid.utils.PixabayUtils
import retrofit2.HttpException
import java.io.IOException


/**
 * provides the data source for paging lib from api calls
 */
@ExperimentalPagingApi
class PixabayImageDBPagingSource(
    val pixabayAppDb: AppDatabase,
    val pixabayService: PixabayService
) :
    PagingSource<Int, PixabayModel>() {

    /**
     * calls api if there is any error getting results then return the [LoadResult.Error]
     * for successful response return the results using [LoadResult.Page] for some reason if the results
     * are empty from service like in case of no more data from api then we can pass [null] to
     * send signal that source has reached the end of list
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PixabayModel> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            var response = pixabayAppDb.getPixabayDao().getAllList()
            if (response.isNullOrEmpty()) {
                response = pixabayService.getPixabayPhotos(
                    page = page,
                    apiKey = BuildConfig.API_KEY,
                    perPage = PixabayRepository.DEFAULT_PAGE_SIZE,
                    query = PixabayUtils.SEARCH, //can also be passed using param as well to enable dynamic search functionality
                    imageType = PixabayUtils.IMAGE_TYPE
                ).hits
            }
            LoadResult.Page(
                response, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PixabayModel>): Int? {
        return state.anchorPosition
    }

}