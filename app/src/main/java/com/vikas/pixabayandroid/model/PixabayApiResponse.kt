package com.vikas.pixabayandroid.model

import androidx.annotation.Keep
import com.vikas.pixabayandroid.persistence.PixabayModel

@Keep
data class PixabayApiResponse(val total: Int, val totalHits: Int, val hits: List<PixabayModel>)