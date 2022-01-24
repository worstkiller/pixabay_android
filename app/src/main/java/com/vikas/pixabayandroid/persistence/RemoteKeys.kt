package com.vikas.pixabayandroid.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(@PrimaryKey val repoId: Int, val prevKey: Int?, val nextKey: Int?)