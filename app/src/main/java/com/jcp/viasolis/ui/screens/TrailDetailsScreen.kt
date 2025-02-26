package com.jcp.viasolis.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Text

import com.jcp.viasolis.R

@Composable
fun TrailDetailsScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = stringResource(id = R.string.trail_details_title))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("navigation") }) {
            Text(stringResource(id = R.string.start_navigation))
        }
    }
}