package com.vikas.pixabayandroid.dependencies

import androidx.paging.ExperimentalPagingApi
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.vikas.pixabayandroid.PixabayApp
import com.vikas.pixabayandroid.persistence.AppDatabase
import com.vikas.pixabayandroid.ui.di.MainActivityModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RoomModule::class,
        NetworkModule::class,
        ActivityBinder::class]
)
interface AppComponent {

    fun retrofit(): Retrofit

    fun gson(): Gson

    fun roomDb(): AppDatabase

    fun inject(application: PixabayApp)

}