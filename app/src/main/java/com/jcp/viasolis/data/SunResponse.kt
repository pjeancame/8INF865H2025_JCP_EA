package com.jcp.viasolis.data

data class SunResponse(
    val results: SunTimes
)

data class SunTimes(
    val sunrise: String,
    val sunset: String
)