package com.smdev.universe.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PlanetsApiClient {

    private var retrofit: Retrofit = getClient()

    private fun getClient(): Retrofit {
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val planetsService = retrofit.create(PlanetsApiInterface::class.java)
}