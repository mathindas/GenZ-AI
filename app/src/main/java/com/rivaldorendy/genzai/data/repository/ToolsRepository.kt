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
        // For demo purposes, we'll create multiple instances of the same tool
        // to match the screenshot
        val aiNgelesPro = AITool(
            id = "ai_ngeles_pro",
            name = "AI Ngeles Pro",
            description = "Create creative excuses effortlessly",
            icon = R.drawable.ic_excuse,
            route = Screen.AINgelesPro.route
        )
        
        // Create a list with 9 copies of the tool to match the screenshot
        val tools = mutableListOf<AITool>()
        repeat(9) { index ->
            tools.add(aiNgelesPro.copy(id = "${aiNgelesPro.id}_$index"))
        }
        
        return tools
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