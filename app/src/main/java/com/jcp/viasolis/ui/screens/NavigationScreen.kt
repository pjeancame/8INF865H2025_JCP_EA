package com.jcp.viasolis.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jcp.viasolis.data.getTrailById
import com.jcp.viasolis.R

@Composable
fun NavigationScreen(navController: NavController, trailId: String?) {
    val trail = remember { getTrailById(trailId) }

    if (trail == null) {
        Text(text = "Sentier introuvable", fontSize = 18.sp)
        return
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Bouton retour
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Retour",
                    tint = Color.Black
                )
            }
        }

        // Carte IGN
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 16.dp)
                .background(Color.Gray)
        ) {
            Text(
                text = "Carte IGN ici",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section de départ
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Partir de :", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /* Sélection du point de départ */ }, modifier = Modifier.fillMaxWidth()) {
                    Text("Sélectionner le point de départ")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Guider vers le point de départ */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Guider vers point de départ", fontSize = 18.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section du sentier
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = trail.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Démarrer la randonnée */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Démarrer Randonnée", fontSize = 18.sp)
                }
            }
        }
    }
}