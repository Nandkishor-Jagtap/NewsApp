package com.example.session16newsapp.domain

import androidx.lifecycle.MutableLiveData
import com.example.session16newsapp.data.NewsResponse
import com.example.session16newsapp.network.ApiBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private var newsResponseLiveData: MutableLiveData<NewsResponse> = MutableLiveData()

    private fun getBreakingNews(country:String) {
        //This line invokes the getTopHeadlines method of the newsApi object, which is an instance of the NewsApi interface created using Retrofit.
        // It passes two parameters: the country code (e.g., "US") and an API key.
        val call = ApiBuilder.retrofitBuilder.getTopHeadlines(country = country)
        //The enqueue method is used to make the API call asynchronously.
        // It takes a Callback as a parameter, defining how to handle the response or failure.
        call.enqueue(object : Callback<NewsResponse> {

            //onResponse method:
            //
            //This method is called when the API call is successful and a response is received.
            //if (response.isSuccessful) checks if the HTTP response code indicates success (status code 2xx).
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                //Inside the if block:
                //val articles = response.body()?.articles ?: emptyList() extracts the list of articles from the response body.
                // If the response body or the articles list is null, it defaults to an empty list.
                if (response.isSuccessful) {
                    newsResponseLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}