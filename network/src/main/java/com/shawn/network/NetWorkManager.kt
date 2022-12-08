package com.shawn.network

import com.shawn.network.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetWorkManager {
    private const val TIME_OUT_SEC = 3000L
    private val baseUrl = "https://www.travel.taipei/open-api/"
    val service: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        getRetrofit().create(ApiService::class.java)
    }


    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClientBuilder())
            .build()
    }

    private fun getHttpClientBuilder(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
        builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        builder.addInterceptor{ chain ->
            chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .build()
                .let(chain::proceed)
        }
        return builder.build()
    }
}