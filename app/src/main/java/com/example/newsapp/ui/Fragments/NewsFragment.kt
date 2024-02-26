package com.example.newsapp.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.Api.ApiManager
import com.example.newsapp.Api.SourceResponse
import com.example.newsapp.Api.SourcesItem
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {
lateinit var binding: FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
binding=FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        ApiManager.webServices().getSources("79f2d8fade644acebc6560c85b8914e2")
            .enqueue(object : Callback<SourceResponse> {
                override fun onResponse(
                    call: Call<SourceResponse>,
                    response: Response<SourceResponse>
                ) {
                    if (response.isSuccessful){
                        response.body()?.sources.let {

                            showTabs(it!!)
                        }

                    }
                    else{

                        Gson().fromJson(response.errorBody()?.string(),SourceResponse::class.java)
                    }

                }

                override fun onFailure(call: Call<SourceResponse>, t: Throwable) {


                }
            })
    }

    private fun showTabs(sources:List<SourcesItem?>) {
 sources?.forEach{
     var tab=binding.Tab.newTab()
     tab.text=it?.name
     binding.Tab.addTab(tab)
 }
    }

    }


