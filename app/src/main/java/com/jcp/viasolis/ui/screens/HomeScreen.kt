package com.jcp.viasolis.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.material3.Text
import com.jcp.viasolis.ui.HikingViewModel
import com.jcp.viasolis.R


@Composable
fun HomeScreen(navController: NavController, hikingViewModel: HikingViewModel = viewModel()) {
    val selectedDay by hikingViewModel.selectedDay.collectAsState()
    val selectedDuration by hikingViewModel.selectedDuration.collectAsState()
    val selectedDistance by hikingViewModel.selectedDistance.collectAsState()
    val sunrise by hikingViewModel.sunrise.collectAsState()
    val sunset by hikingViewModel.sunset.collectAsState()

    val weatherInfo by hikingViewModel.weatherInfo.collectAsState()
    val selectedDayIndex by hikingViewModel.selectedDayIndex.collectAsState()

    LaunchedEffect(selectedDayIndex) {
        hikingViewModel.loadWeather(
            lat = 48.4289, // Chicoutimi
            lon = -71.0596,
            apiKey = "e0ebb395be6e3aa2af887052ea56ea06"
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        // Sélection du jour de la semaine
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { hikingViewModel.changeDay(hikingViewModel.selectedDayIndex.value - 1) }) {
                Icon(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "Jour précédent")
            }
            Text(text = selectedDay, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 16.dp))
            IconButton(onClick = { hikingViewModel.changeDay(hikingViewModel.selectedDayIndex.value + 1) }) {
                Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "Jour suivant")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lever et coucher du soleil
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.ic_sunrise), contentDescription = "Lever du soleil")
                Text(text = "Lever : $sunrise", fontSize = 16.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.ic_sunset), contentDescription = "Coucher du soleil")
                Text(text = "Coucher : $sunset", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section météo
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                weatherInfo?.let {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        WeatherIcon(it.weather.firstOrNull()?.icon ?: "01d")
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "Température : ${it.main.temp}°C", fontSize = 16.sp)
                            Text(text = "Vent : ${it.wind.speed} km/h", fontSize = 16.sp)
                        }
                    }
                } ?: Text(text = "Chargement de la météo...", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sélection des filtres
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Position de départ : Ma position", fontSize = 18.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { hikingViewModel.updateDistance(false) }) {
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "Réduire le périmètre")
                }
                Text(text = "Périmètre : $selectedDistance km", fontSize = 18.sp, modifier = Modifier.padding(horizontal = 16.dp))
                IconButton(onClick = { hikingViewModel.updateDistance(true) }) {
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "Augmenter le périmètre")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { hikingViewModel.updateDuration(false) }) {
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "Réduire la durée")
                }
                Text(text = "Durée max : $selectedDuration h", fontSize = 18.sp, modifier = Modifier.padding(horizontal = 16.dp))
                IconButton(onClick = { hikingViewModel.updateDuration(true) }) {
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "Augmenter la durée")
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Bouton pour trouver une randonnée
        Button(
            onClick = { navController.navigate("trails") },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = stringResource(id = R.string.view_trails), fontSize = 18.sp)
        }
    }
}

@Composable
fun WeatherIcon(iconCode: String) {
    val iconResId = when (iconCode) {
        "01d" -> R.drawable.ic_clear_day
        "01n" -> R.drawable.ic_clear_night
        "02d", "02n" -> R.drawable.ic_partly_cloudy
        "03d", "03n", "04d", "04n" -> R.drawable.ic_cloudy
        "09d", "09n", "10d", "10n" -> R.drawable.ic_rain
        "11d", "11n" -> R.drawable.ic_storm
        "13d", "13n" -> R.drawable.ic_snow
        "50d", "50n" -> R.drawable.ic_mist
        else -> R.drawable.ic_clear_day
    }

    Image(
        painter = painterResource(id = iconResId),
        contentDescription = "Météo : $iconCode",
        modifier = Modifier.size(48.dp)
    )
}
