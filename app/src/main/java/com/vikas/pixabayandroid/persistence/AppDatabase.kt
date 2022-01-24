package com.vikas.pixabayandroid.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PixabayModel::class, RemoteKeys::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRemoteDao(): RemoteKeysDao
    abstract fun getPixabayDao(): PixabayDao

}