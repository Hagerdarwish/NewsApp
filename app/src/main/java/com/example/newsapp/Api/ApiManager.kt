package com.example.newsapp.Api

import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
// the aim of api manager to return webservice to access on it
//Api manager :responsbile to make object from webservice to access on it
// using retrofit
//retrofit:it simplfy process to interact with api
// the process : 1- make webservice interface have our api which is sources and everything with their link
object ApiManager {
  private  var retrofit: Retrofit? =null
    fun webServices():WebServices {
        if (retrofit==null){
            val loggingInterceptor=HttpLoggingInterceptor{
                Log.e("Api manager","body is $it")
            }
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClint=OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
             retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(okHttpClint)
                .build()
        }
        // implementation of the WebService interface.
        return retrofit!!.create(WebServices::class.java)

    }

}