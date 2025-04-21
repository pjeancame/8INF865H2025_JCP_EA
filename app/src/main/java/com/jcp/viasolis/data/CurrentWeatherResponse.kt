package com.jcp.viasolis.data

data class CurrentWeatherResponse(
    val sys: SysInfo
)

data class SysInfo(
    val sunrise: Long,
    val sunset: Long
)
