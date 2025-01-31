package com.example.rma24projekat_19153

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val urlWithApiKey = originalRequest.url.newBuilder()
            .addQueryParameter("token", apiKey)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

   //     Log.d("ApiKeyInterceptor", "URL with API key: ${newRequest.url}")

        return chain.proceed(newRequest)
    }
}