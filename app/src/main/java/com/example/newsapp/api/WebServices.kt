package com.example.newsapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("/v2/top-headlines/sources")
    fun getSources(@Query("apiKey") apiKey:String):retrofit2.Call<SourceResponse>
    @GET("/v2/everything")
    fun getEveryThing(@Query("apiKey") apiKey:String):retrofit2.Call<ArticleResponse>
}