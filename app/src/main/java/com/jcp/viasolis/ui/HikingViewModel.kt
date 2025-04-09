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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class HikingViewModel : ViewModel() {
    // Liste des jours de la semaine
    private val daysOfWeek = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")

    // Stocke l'index du jour sélectionné
    private val todayIndex = Calendar.getInstance().get(Calendar.DAY_OF_WEEK).let {
        (it + 5) % 7 // convertit de 1-7 (dim-lun) vers 0-6 (lun-dim)
    }
    private val _selectedDayIndex = MutableStateFlow(todayIndex)
    val selectedDayIndex: StateFlow<Int> = _selectedDayIndex

    // Expose le jour sélectionné sous forme de texte (écoute _selectedDayIndex)
    val selectedDay: StateFlow<String> = _selectedDayIndex.map { daysOfWeek[it] }.stateIn(
        scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Default),
        started = SharingStarted.Lazily,
        initialValue = daysOfWeek[0]
    )

    private val _sunrise = MutableStateFlow("")
    val sunrise: StateFlow<String> = _sunrise

    private val _sunset = MutableStateFlow("")
    val sunset: StateFlow<String> = _sunset

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
        val maxAllowed = getMaxHikeDuration()
        if (newDuration in 1..maxAllowed) {
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
                val forecastResponse = RetrofitInstance.api.getForecast(lat, lon, apiKey)
                val currentWeather = RetrofitInstance.api.getCurrentWeather(lat, lon, apiKey)

                // Récupération du jour sélectionné
                val dayIndex = _selectedDayIndex.value

                // Filtrage de la météo pour le bon jour
                val cal = Calendar.getInstance()
                val todayIndex = Calendar.getInstance().get(Calendar.DAY_OF_WEEK).let {
                    (it + 5) % 7
                }
                val dayOffset = dayIndex - todayIndex
                cal.add(Calendar.DAY_OF_YEAR, dayOffset)

                val targetDateString = SimpleDateFormat("yyyy-MM-dd").format(cal.time)

                val weatherForSelectedDay = forecastResponse.list.firstOrNull { item ->
                    item.dt_txt.startsWith(targetDateString)
                }

                val sunResponse = RetrofitInstance.sunApi.getSunInfo(
                    lat = lat,
                    lng = lon,
                    date = targetDateString
                )

                _sunrise.value = formatIsoTimeToLocal(sunResponse.results.sunrise)
                _sunset.value = formatIsoTimeToLocal(sunResponse.results.sunset)


                weatherForSelectedDay?.let {
                    _weatherInfo.value = it
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun formatUnixTime(unixTime: Long): String {
        val date = Date(unixTime * 1000)
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("America/Toronto") // fuseau horaire de Chicoutimi
        return format.format(date)
    }

    private fun formatIsoTimeToLocal(isoTime: String): String {
        val cleanedIso = isoTime.replace("+00:00", "+0000") // Hack pour API < 24
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("UTC")

        val date = parser.parse(cleanedIso)
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("America/Toronto")

        return formatter.format(date!!)
    }

    private fun parseTimeToFloat(time: String): Float {
        val parts = time.split(":")
        if (parts.size != 2) return 0f

        val hours = parts[0].toFloatOrNull() ?: 0f
        val minutes = parts[1].toFloatOrNull() ?: 0f

        return hours + (minutes / 60f)
    }

    private fun getMaxHikeDuration(): Int {
        if (_sunrise.value.isBlank() || _sunset.value.isBlank()) {
            return 10 // valeur par défaut temporaire si pas encore chargé
        }

        val sunriseTime = parseTimeToFloat(_sunrise.value)
        val sunsetTime = parseTimeToFloat(_sunset.value)
        val duration = sunsetTime - sunriseTime

        if (duration <= 0f) return 10 // sécurité si valeurs incohérentes

        val rounded = (duration * 2).toInt() / 2f
        return rounded.toInt()
    }


}
