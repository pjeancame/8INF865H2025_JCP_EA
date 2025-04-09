package com.jcp.viasolis.data

// Modèle de données pour un sentier
data class Trail(
    val id: String, // Ajout d'un ID unique pour faciliter la navigation
    val name: String,
    val duration: String,
    val distance: String,
    val elevation: String,
    val difficulty: Int,
    val description: String,
    val latitude: Double,
    val longitude: Double
)