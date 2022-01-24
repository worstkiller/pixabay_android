package com.vikas.pixabayandroid.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PixabayModel(
    @PrimaryKey val id: Int,
    val previewURL: String,
    val views: Int,
    val user: String
)