package com.vikas.pixabayandroid.persistence

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PixabayDao {

    @Query("SELECT * FROM pixabaymodel")
    fun getAll(): PagingSource<Int, PixabayModel>

    @Query("SELECT * FROM pixabaymodel WHERE id IN (:id)")
    fun loadAllByIds(id: IntArray): List<PixabayModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pixabayModels: List<PixabayModel>)

    @Query("DELETE FROM pixabaymodel")
    fun clearAll(): Int

}