package com.vikas.pixabayandroid.dependencies

import androidx.paging.ExperimentalPagingApi
import com.vikas.pixabayandroid.ui.home.MainActivity
import com.vikas.pixabayandroid.ui.di.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Module
abstract class ActivityBinder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindsMainActivity(): MainActivity

}