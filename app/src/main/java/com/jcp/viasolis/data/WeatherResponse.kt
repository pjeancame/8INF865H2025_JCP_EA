package com.jcp.viasolis.data

data class WeatherResponse(
    val list: List<WeatherData>
)

data class WeatherData(
    val dt_txt: String,
    val main: MainInfo,
    val wind: WindInfo
)

data class MainInfo(
    val temp: Float
)

data class WindInfo(
    val speed: Float
)
