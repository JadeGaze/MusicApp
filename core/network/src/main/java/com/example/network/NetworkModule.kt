package com.example.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    single<OkHttpClient> {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.build()
    }

    single<DeezerApi> {
        val networkJson = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl(BuildConfig.DEEZER_API_BASE_URL)
            .client(get())
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(DeezerApi::class.java)
    }

}