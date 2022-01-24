package com.vikas.pixabayandroid

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.vikas.pixabayandroid.dependencies.AppComponent
import com.vikas.pixabayandroid.dependencies.AppModule
import com.vikas.pixabayandroid.dependencies.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class PixabayApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

}