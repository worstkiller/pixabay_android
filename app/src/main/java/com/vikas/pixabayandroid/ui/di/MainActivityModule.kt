package com.vikas.pixabayandroid.ui.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.vikas.pixabayandroid.api.PixabayService
import com.vikas.pixabayandroid.persistence.AppDatabase
import com.vikas.pixabayandroid.repo.PixabayRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Module
class MainActivityModule {

    @Provides
    @Singleton
    fun providesPixabayRepository(
        appDatabase: AppDatabase,
        pixabayService: PixabayService,
        context: Context
    ): PixabayRepository {
        return PixabayRepository(pixabayService, appDatabase, context)
    }
}