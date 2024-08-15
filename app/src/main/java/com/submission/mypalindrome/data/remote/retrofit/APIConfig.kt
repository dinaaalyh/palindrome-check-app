package com.submission.mypalindrome.data.remote.retrofit

import com.submission.mypalindrome.BuildConfig.BASE_URL
import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIConfig {
    fun getApiService(): APIService {
        val interceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofitInstance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        return retrofitInstance.create(APIService::class.java)
    }
}