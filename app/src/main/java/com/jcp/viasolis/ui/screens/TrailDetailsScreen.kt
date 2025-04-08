package com.jcp.viasolis.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jcp.viasolis.ui.components.DifficultyIndicator
import com.jcp.viasolis.ui.components.InfoBox
import com.jcp.viasolis.data.getTrailById

import com.jcp.viasolis.R
import com.jcp.viasolis.ui.components.InfoBoxText

@Composable
fun TrailDetailsScreen(navController: NavController, trailId: String?) {
    val trail = remember { getTrailById(trailId) }
    val scrollState = rememberScrollState()

    if (trail == null) {
        Text(text = "Sentier introuvable", fontSize = 18.sp)
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
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

        // Image du sentier
        Image(
            painter = painterResource(id = R.drawable.trail_image_placeholder),
            contentDescription = "Image du sentier",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Titre du sentier
        Text(
            text = trail.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Informations de la randonnée
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InfoBox("Difficulté") { DifficultyIndicator(trail.difficulty) }
                    InfoBoxText("Longueur", trail.distance)
                    InfoBoxText("Durée", trail.duration)
                    InfoBoxText("Dénivelé", trail.elevation)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description du sentier
        Text(
            text = trail.description,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Carte IGN du tracé du sentier
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp) // Carte plus grande
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

        // Bouton de guidage
        Button(
            onClick = { navController.navigate("navigation/${trail.id}") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Démarrer le guidage", fontSize = 18.sp)
        }
    }
}