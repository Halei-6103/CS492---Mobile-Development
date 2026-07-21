package com.example.assignment4.coffee.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignment4.coffee.data.CoffeeRepository
import com.example.assignment4.coffee.ui.screens.CoffeeShopDetailScreen
import com.example.assignment4.coffee.ui.screens.CoffeeShopListScreen
import com.example.assignment4.coffee.ui.screens.HomeScreen
import com.example.assignment4.coffee.ui.screens.SettingsScreen
import com.example.assignment4.coffee.ui.screens.TranslationScreen
import com.example.assignment4.coffee.ui.settings.FontSizeOption

@Composable
fun CoffeeNavGraph(
    navController: NavHostController = rememberNavController(),
    currentFontSize: FontSizeOption,
    onFontSizeSelected: (FontSizeOption) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = CoffeeRoutes.HOME
    ) {
        composable(CoffeeRoutes.HOME) {
            HomeScreen(
                onBrowseShops = { navController.navigate(CoffeeRoutes.SHOP_LIST) },
                onBack = { navController.popBackStack() },
                onSettings = { navController.navigate(CoffeeRoutes.SETTINGS) }
            )
        }
        composable(CoffeeRoutes.SHOP_LIST) {
            CoffeeShopListScreen(
                shops = CoffeeRepository.getAllShops(),
                onShopClick = { id -> navController.navigate(CoffeeRoutes.shopDetail(id)) },
                onBack = { navController.popBackStack() },
                onSettings = { navController.navigate(CoffeeRoutes.SETTINGS) }
            )
        }
        composable(
            CoffeeRoutes.SHOP_DETAIL,
            arguments = listOf(navArgument("shopId") { type = NavType.StringType })
        ) { backStackEntry ->
            val shopId = backStackEntry.arguments?.getString("shopId")
            val shop = shopId?.let { CoffeeRepository.getShopById(it) }
            CoffeeShopDetailScreen(
                shop = shop,
                onTranslateMenu = { navController.navigate(CoffeeRoutes.TRANSLATION) },
                onBack = { navController.popBackStack() },
                onSettings = { navController.navigate(CoffeeRoutes.SETTINGS) }
            )
        }
        composable(CoffeeRoutes.TRANSLATION) {
            TranslationScreen(
                onBack = { navController.popBackStack() },
                onSettings = { navController.navigate(CoffeeRoutes.SETTINGS) }
            )
        }
        composable(CoffeeRoutes.SETTINGS) {
            SettingsScreen(
                currentFontSize = currentFontSize,
                onFontSizeSelected = onFontSizeSelected,
                onBack = { navController.popBackStack() },
                onSettings = { }
            )
        }
    }
}
