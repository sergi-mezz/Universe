package com.smdev.universe.api


import com.smdev.universe.data.Planet
import com.smdev.universe.data.PlanetsListServiceResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface PlanetsApiInterface {

    @GET("planets")
    suspend fun getPlanets(): PlanetsListServiceResponse

    @GET()
    suspend fun getPlanet(@Url url: String): Planet
}