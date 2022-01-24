package com.vikas.pixabayandroid.ui.di

import androidx.paging.ExperimentalPagingApi
import com.vikas.pixabayandroid.api.PixabayService
import com.vikas.pixabayandroid.persistence.AppDatabase
import com.vikas.pixabayandroid.repo.PixabayRepository
import com.vikas.pixabayandroid.ui.home.PixabayImageViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Module
class MainActivityModule {

    @Provides
    fun providesPixabayRepository(retrofit: Retrofit, appDatabase: AppDatabase): PixabayRepository {
        return PixabayRepository(retrofit.create(PixabayService::class.java), appDatabase)
    }

    @Provides
    fun providesPixabayImageViewModel(repository: PixabayRepository): PixabayImageViewModel {
        return PixabayImageViewModel(repository)
    }

}