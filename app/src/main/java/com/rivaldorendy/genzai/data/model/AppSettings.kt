package com.rivaldorendy.genzai.data.model

/**
 * Data class representing the app settings
 * 
 * @property language Selected language ("en" for English, "id" for Indonesian)
 * @property isDarkMode Whether dark mode is enabled
 * @property apiKey Custom Gemini API key (if provided)
 */
data class AppSettings(
    val language: String = "en",
    val isDarkMode: Boolean = false,
    val apiKey: String = ""
) 