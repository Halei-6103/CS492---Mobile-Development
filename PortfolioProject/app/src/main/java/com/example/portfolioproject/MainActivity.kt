/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */

package com.example.portfolioproject


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.portfolioproject.ui.GameViewModel
import com.example.portfolioproject.ui.GameViewModelFactory
import com.example.portfolioproject.ui.navigation.AppNavigation
import com.example.portfolioproject.ui.theme.PortfolioProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PortfolioProjectTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val viewModel: GameViewModel = viewModel(
                        factory = GameViewModelFactory(application)
                    )
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}
