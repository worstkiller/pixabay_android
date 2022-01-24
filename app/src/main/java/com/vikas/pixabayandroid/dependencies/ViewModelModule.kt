package com.vikas.pixabayandroid.dependencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.vikas.pixabayandroid.ui.home.PixabayImageViewModel
import com.vikas.pixabayandroid.viewmodel.PixabayViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PixabayImageViewModel::class)
    abstract fun bindUserViewModel(pixabayImageViewModel: PixabayImageViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: PixabayViewModelFactory): ViewModelProvider.Factory
}