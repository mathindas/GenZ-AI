package com.rivaldorendy.genzai.ui.navigation

/**
 * Sealed class representing the different screens in the app
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("settings")
    object About : Screen("about")
    object AINgelesPro : Screen("ai_ngeles_pro")
} 