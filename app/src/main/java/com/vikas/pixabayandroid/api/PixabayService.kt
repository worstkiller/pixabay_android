package com.vikas.pixabayandroid.api

import com.vikas.pixabayandroid.model.PixabayApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

    @GET("api/")
    suspend fun getPixabayPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("image_type") imageType: String
    ): PixabayApiResponse

}