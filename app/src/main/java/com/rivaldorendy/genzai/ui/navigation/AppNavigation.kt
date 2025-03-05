package com.rivaldorendy.genzai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rivaldorendy.genzai.data.model.AppSettings
import com.rivaldorendy.genzai.ui.screens.about.AboutScreen
import com.rivaldorendy.genzai.ui.screens.aingeles.AINgelesProScreen
import com.rivaldorendy.genzai.ui.screens.home.HomeScreen
import com.rivaldorendy.genzai.ui.screens.settings.SettingsScreen
import kotlinx.coroutines.flow.StateFlow

/**
 * Main navigation component for the app
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
    settingsFlow: StateFlow<AppSettings>,
    onSaveSettings: (AppSettings) -> Unit
) {
    val settings by settingsFlow.collectAsState()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToTool = { route -> navController.navigate(route) }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                settings = settings,
                onSaveSettings = onSaveSettings,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAbout = { navController.navigate(Screen.About.route) }
            )
        }
        
        composable(Screen.About.route) {
            AboutScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.AINgelesPro.route) {
            AINgelesProScreen(
                settings = settings,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
} 