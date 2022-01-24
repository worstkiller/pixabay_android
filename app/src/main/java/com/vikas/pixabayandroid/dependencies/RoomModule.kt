package com.vikas.pixabayandroid.dependencies

import android.content.Context
import androidx.room.Room
import com.vikas.pixabayandroid.persistence.AppDatabase
import com.vikas.pixabayandroid.utils.PixabayConstants.DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun providesRoom(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
    }

}