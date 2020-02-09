package com.postgram.android.data.Remote

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {

    private val API_URL = "https://stark-spire-93433.herokuapp.com/"
    private var retrofit: Retrofit? = null
    fun getClient(): Retrofit {
        if (retrofit == null) {

            val protocolList =
                mutableListOf<Protocol>(Protocol.HTTP_2, Protocol.HTTP_1_1, Protocol.SPDY_3)
            /* certPinner = CertificatePinner.Builder()
                              .add(HOSTING_NAME, SHA_KEY)
                              .build()*/
            val clientBuilder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .protocols(protocolList)
                // .certificatePinner(certPinner)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)

            retrofit = Retrofit.Builder()
                .client(clientBuilder.build())
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }


}



