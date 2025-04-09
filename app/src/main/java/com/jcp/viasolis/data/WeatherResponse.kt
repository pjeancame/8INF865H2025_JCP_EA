package com.jcp.viasolis.data

data class WeatherResponse(
    val city: CityInfo,
    val list: List<WeatherData>
)

data class WeatherData(
    val dt_txt: String,
    val main: MainInfo,
    val wind: WindInfo,
    val weather: List<WeatherDescription>
)

data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)

data class MainInfo(
    val temp: Float
)

data class WindInfo(
    val speed: Float
)

data class CityInfo(
    val sunrise: Long,
    val sunset: Long
)
