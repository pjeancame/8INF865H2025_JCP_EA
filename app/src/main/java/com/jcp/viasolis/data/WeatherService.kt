package com.jcp.viasolis.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    // Météo à 5 jours (déjà existant)
    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    // Météo actuelle (nouvel ajout pour sunrise/sunset)
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): CurrentWeatherResponse
}