package com.example.session16newsapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.session16newsapp.data.NewsResponse
import com.example.session16newsapp.databinding.FragmentNewsBinding
import com.example.session16newsapp.network.ApiBuilder
import com.example.session16newsapp.presentation.adapter.NewsAdapter
import com.example.session16newsapp.presentation.viewmodel.NewsViewModel
import com.example.session16newsapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]


        initViews()
        setUpApiCall()

        return binding.root
    }


    private fun setUpApiCall() {
        //This line invokes the getTopHeadlines method of the newsApi object, which is an instance of the NewsApi interface created using Retrofit.
        // It passes two parameters: the country code (e.g., "US") and an API key.
        val call = ApiBuilder.retrofitBuilder.getTopHeadlines(Constants.US, Constants.API_KEY)
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
                    val articles = response.body()?.articles ?: emptyList()
                    //adapter.updateList(articles) updates the data in the adapter with the new list of articles.
                    adapter.updateList(articles)
                } else {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Something went wrong ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    private fun initViews(){
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        adapter = NewsAdapter(arrayListOf())
        binding.recyclerView.adapter = adapter
    }
}