package com.vikas.pixabayandroid.repo.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vikas.pixabayandroid.BuildConfig
import com.vikas.pixabayandroid.utils.PixabayConstants
import com.vikas.pixabayandroid.api.PixabayService
import com.vikas.pixabayandroid.persistence.AppDatabase
import com.vikas.pixabayandroid.persistence.PixabayModel
import com.vikas.pixabayandroid.persistence.RemoteKeys
import com.vikas.pixabayandroid.repo.PixabayRepository.Companion.DEFAULT_PAGE_INDEX
import com.vikas.pixabayandroid.utils.PixabayConstants.SEARCH
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class PixabayMediator(val pixabayService: PixabayService, val appDatabase: AppDatabase) :
    RemoteMediator<Int, PixabayModel>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, PixabayModel>
    ): MediatorResult {

        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = pixabayService.getPixabayPhotos(
                page = page,
                apiKey = BuildConfig.API_KEY,
                perPage = state.config.pageSize,
                query = SEARCH,
                imageType = PixabayConstants.IMAGE_TYPE
            )
            val isEndOfList = response.hits.isEmpty()
            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.getRemoteDao().clearRemoteKeys()
                    appDatabase.getPixabayDao().clearAll()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.hits.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.getRemoteDao().insertAll(keys)
                appDatabase.getPixabayDao().insertAll(response.hits)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, PixabayModel>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                //end of list condition reached
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, PixabayModel>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { doggo -> appDatabase.getRemoteDao().remoteKeysPixaId(doggo.id) }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, PixabayModel>): RemoteKeys? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { doggo -> appDatabase.getRemoteDao().remoteKeysPixaId(doggo.id) }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, PixabayModel>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                appDatabase.getRemoteDao().remoteKeysPixaId(repoId)
            }
        }
    }

}