package com.example.session16newsapp.network

import com.example.session16newsapp.data.NewsResponse
import com.example.session16newsapp.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String=Constants.API_KEY,
    ): Call<NewsResponse>





}