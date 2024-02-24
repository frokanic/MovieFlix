package com.projects.movieflix.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.projects.movieflix.presentation.navigation.Navigation
import com.projects.movieflix.presentation.ui.theme.MovieFlixTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {  }

        setContent {
            MovieFlixTheme {
                Navigation()
            }
        }
    }
}