package com.example.newsapp.ui.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.ArticleResponse
import com.example.newsapp.api.SourceResponse
import com.example.newsapp.api.SourcesItem
import com.example.newsapp.databinding.FragmentNewsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
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
        initListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        changeErrorVisibity(true)
        changeErrorVisibity(false)
        ApiManager.webServices().getSources("79f2d8fade644acebc6560c85b8914e2")
            .enqueue(object : Callback<SourceResponse> {
                @SuppressLint("SuspiciousIndentation")
                override fun onResponse(
                    call: Call<SourceResponse>,
                    response: Response<SourceResponse>
                ) {
                    changeErrorVisibity(false)
                    if (response.isSuccessful){

                        response.body()?.sources.let {

                            showTabs(it!!)
                        }

                    }
                    else{

                      val res= Gson().fromJson(response.errorBody()?.string(),SourceResponse::class.java)
                        changeErrorVisibity(true)
                    }

                }

                override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                    changeErrorVisibity(true,t.localizedMessage ?:"something went error")

                }
            })
    }

    private fun showTabs(sources:List<SourcesItem?>) {
 sources?.forEach{
     var onetab=binding.Tab.newTab()
     onetab.text=it?.name
     binding.Tab.addTab(onetab)
     onetab.tag=sources
 }
    }
  private fun changeErrorVisibity(isVisible:Boolean,message:String=""){
        binding.errorView.root.isVisible=isVisible
        binding.loading.isVisible=isVisible
      if (isVisible){
          binding.errorView.errorMessage.text=message
      }

    }
    fun initListener(){
        binding.errorView.btnError.setOnClickListener {
            loadData()
        }
        binding.Tab.addOnTabSelectedListener(object :OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var source= tab?.tag as SourcesItem
                source.id?.let {
                    loadArticle(it)
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun loadArticle(sourceId:String) {
        ApiManager.webServices().getEveryThing("79f2d8fade644acebc6560c85b8914e2")
            .enqueue(object :Callback<ArticleResponse>{
                override fun onResponse(
                    call: Call<ArticleResponse>,
                    response: Response<ArticleResponse>
                ) {
                    if (response.isSuccessful){

                    }else{
                        val res= Gson().fromJson(response.errorBody()?.string(),ArticleResponse::class.java)
                        changeErrorVisibity(true)

                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

    }

}


