package com.rivaldorendy.genzai.data.repository

import com.rivaldorendy.genzai.R
import com.rivaldorendy.genzai.data.model.AITool
import com.rivaldorendy.genzai.ui.navigation.Screen
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing AI tools
 */
@Singleton
class ToolsRepository @Inject constructor() {
    
    /**
     * Get all available AI tools
     */
    fun getTools(): List<AITool> {
        return listOf(
            AITool(
                id = "ai_ngeles_pro",
                name = "AI Ngeles Pro",
                description = "Create creative excuses effortlessly",
                icon = R.drawable.ic_excuse, // We'll create this drawable later
                route = Screen.AINgelesPro.route
            )
            // More tools can be added here in the future
        )
    }
    
    /**
     * Search for tools by name or description
     */
    fun searchTools(query: String): List<AITool> {
        if (query.isBlank()) return getTools()
        
        return getTools().filter { tool ->
            tool.name.contains(query, ignoreCase = true) || 
            tool.description.contains(query, ignoreCase = true)
        }
    }
} 