package com.jcp.viasolis.data

import retrofit2.http.GET
import retrofit2.http.Query

interface SunService {
    @GET("json")
    suspend fun getSunInfo(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("date") date: String, // Format yyyy-MM-dd
        @Query("formatted") formatted: Int = 0 // Pour obtenir l'heure en ISO 8601
    ): SunResponse
}