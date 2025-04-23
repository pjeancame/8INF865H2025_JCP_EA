package com.jcp.viasolis.ui.screens

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
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

@Composable
fun NavigationScreen(navController: NavController, trailId: String?) {
    val trail = remember { getTrailById(trailId) }
    val context = LocalContext.current

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

        // Titre du sentier en haut
        Text(
            text = trail.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color.Black
        )

        // Carte IGN
        val trailLatLng = LatLng(trail.latitude, trail.longitude)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(trailLatLng, 14f)
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 16.dp),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = rememberMarkerState(position = trailLatLng),
                title = trail.name
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
                Text(text = "Début du sentier", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val uri = Uri.parse("google.navigation:q=${trail.latitude},${trail.longitude}")
                        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                            setPackage("com.google.android.apps.maps")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Guider vers point de départ", fontSize = 18.sp)
                }
            }
        }
    }
}
