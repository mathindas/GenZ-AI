package com.rivaldorendy.genzai.data.model

/**
 * Data class representing an AI tool in the app
 * 
 * @property id Unique identifier for the tool
 * @property name Name of the tool
 * @property description Short description of the tool
 * @property icon Icon resource ID for the tool
 * @property route Navigation route to the tool's screen
 */
data class AITool(
    val id: String,
    val name: String,
    val description: String,
    val icon: Int,
    val route: String
) 