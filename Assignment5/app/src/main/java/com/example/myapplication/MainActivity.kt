/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */
package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.CityCategoriesScreen
import com.example.myapplication.ui.MyCityViewModel
import com.example.myapplication.ui.RecommendationDetailScreen
import com.example.myapplication.ui.RecommendationsScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

object Routes {
    const val CATEGORIES = "categories"
    const val RECOMMENDATIONS = "recommendations"
    const val DETAIL = "detail"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: MyCityViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyCityNavHost(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyCityNavHost(
    viewModel: MyCityViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.CATEGORIES,
        modifier = modifier
    ) {
        composable(Routes.CATEGORIES) {
            CityCategoriesScreen(
                viewModel = viewModel,
                onCategoryClick = { category ->
                    viewModel.selectCategory(category.id)
                    navController.navigate(Routes.RECOMMENDATIONS)
                }
            )
        }
        composable(Routes.RECOMMENDATIONS) {
            BackHandler {
                viewModel.backToCategories()
                navController.popBackStack()
            }
            RecommendationsScreen(
                viewModel = viewModel,
                onRecommendationClick = { rec ->
                    viewModel.selectRecommendation(rec.id)
                    navController.navigate(Routes.DETAIL)
                }
            )
        }
        composable(Routes.DETAIL) {
            BackHandler {
                viewModel.backToRecommendations()
                navController.popBackStack()
            }
            RecommendationDetailScreen(viewModel = viewModel)
        }
    }
}
