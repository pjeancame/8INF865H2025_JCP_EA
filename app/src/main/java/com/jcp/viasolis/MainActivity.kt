package com.jcp.viasolis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jcp.viasolis.navigation.AppNavigation
import com.jcp.viasolis.ui.theme.ViaSolisTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViaSolisTheme {
                AppNavigation()
            }
        }
    }
}

