package com.jcp.viasolis.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.*
import com.jcp.viasolis.data.RetrofitInstance
import com.jcp.viasolis.data.WeatherData
import com.jcp.viasolis.data.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HikingViewModel : ViewModel() {
    // Liste des jours de la semaine
    private val daysOfWeek = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")

    // Stocke l'index du jour sélectionné
    private val _selectedDayIndex = MutableStateFlow(0)
    val selectedDayIndex: StateFlow<Int> = _selectedDayIndex

    // Expose le jour sélectionné sous forme de texte (écoute _selectedDayIndex)
    val selectedDay: StateFlow<String> = _selectedDayIndex.map { daysOfWeek[it] }.stateIn(
        scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Default),
        started = SharingStarted.Lazily,
        initialValue = daysOfWeek[0]
    )

    // Fonction pour modifier le jour sélectionné
    fun changeDay(newIndex: Int) {
        if (newIndex in daysOfWeek.indices) {
            _selectedDayIndex.value = newIndex
        }
    }

    // Stocke les filtres de randonnée
    private val _selectedDuration = MutableStateFlow(2) // 2h par défaut
    val selectedDuration: StateFlow<Int> = _selectedDuration

    private val _selectedDistance = MutableStateFlow(10) // 10 km par défaut
    val selectedDistance: StateFlow<Int> = _selectedDistance

    // Fonction pour mettre à jour la durée max de rando
    fun updateDuration(increment: Boolean) {
        val newDuration = _selectedDuration.value + if (increment) 1 else -1
        if (newDuration in 1..10) { // Limite entre 1h et 10h
            _selectedDuration.value = newDuration
        }
    }

    // Fonction pour mettre à jour la distance max de rando
    fun updateDistance(increment: Boolean) {
        val newDistance = _selectedDistance.value + if (increment) 5 else -5
        if (newDistance in 5..50) { // Limite entre 5 km et 50 km
            _selectedDistance.value = newDistance
        }
    }


    private val _weatherInfo = MutableStateFlow<WeatherData?>(null)
    val weatherInfo: StateFlow<WeatherData?> = _weatherInfo

    fun loadWeather(lat: Double, lon: Double, apiKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getForecast(lat, lon, apiKey)
                _weatherInfo.value = response.list.firstOrNull() // Prochaine tranche horaire
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
