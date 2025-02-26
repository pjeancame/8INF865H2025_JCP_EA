package com.jcp.viasolis.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import com.jcp.viasolis.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jcp.viasolis.ui.HikingViewModel
import kotlinx.coroutines.launch
import com.jcp.viasolis.data.Trail
import com.jcp.viasolis.data.trailsList


@Composable
fun TrailsScreen(navController: NavController, hikingViewModel: HikingViewModel = viewModel()) {
    val selectedDay by hikingViewModel.selectedDay.collectAsState()
    val selectedDuration by hikingViewModel.selectedDuration.collectAsState()
    val selectedDistance by hikingViewModel.selectedDistance.collectAsState()

    val trails = trailsList

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), state = listState) {
        item {
            Text(text = "Randonnées du $selectedDay", fontSize = 22.sp, modifier = Modifier.padding(bottom = 8.dp))
        }

        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Périmètre : $selectedDistance km", fontSize = 16.sp)
                        Text(text = "Durée max : $selectedDuration h", fontSize = 16.sp)
                    }
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(painter = painterResource(id = R.drawable.ic_edit), contentDescription = "Modifier les filtres")
                    }
                }
            }
        }

        items(trails) { trail ->
            TrailItem(trail, onClick = { navController.navigate("trail_details/${trail.id}")
            })
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = {
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                }) {
                    Text("Revenir en haut")
                }
                Button(onClick = { /* Charger plus de sentiers */ }) {
                    Text("Plus de résultats")
                }
            }
        }
    }
}


// Modèle de données pour un sentier
data class Trail(
    val name: String,
    val duration: String,
    val distance: String,
    val elevation: String,
    val difficulty: Int,
    val description: String
)

// Composable pour afficher un élément de sentier
@Composable
fun TrailItem(trail: Trail, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = trail.name, fontSize = 18.sp)
            Text(text = "Durée : ${trail.duration}", fontSize = 16.sp)
            Text(text = "Distance : ${trail.distance}", fontSize = 16.sp)
            Text(text = "Dénivelé : ${trail.elevation}", fontSize = 16.sp)
            DifficultyIndicator(trail.difficulty)
        }
    }
}

// Composable pour afficher le niveau de difficulté avec 4 rectangles remplis
@Composable
fun DifficultyIndicator(difficulty: Int) {
    Row {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .size(20.dp, 10.dp)
                    .padding(end = 4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (index < difficulty) Color.Red else Color.Gray)
            )
        }
    }
}
