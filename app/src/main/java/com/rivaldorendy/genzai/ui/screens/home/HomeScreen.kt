package com.rivaldorendy.genzai.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rivaldorendy.genzai.R
import com.rivaldorendy.genzai.data.model.AITool
import com.rivaldorendy.genzai.ui.components.AppBar
import com.rivaldorendy.genzai.ui.components.SearchBar
import com.rivaldorendy.genzai.ui.components.ToolCard
import com.rivaldorendy.genzai.ui.theme.HomeBackgroundLight
import com.rivaldorendy.genzai.ui.viewmodels.HomeViewModel

/**
 * Home screen displaying a grid of available AI tools
 */
@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToTool: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val tools = remember(searchQuery) {
        viewModel.searchTools(searchQuery)
    }
    
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.app_name),
                showSettingsButton = true,
                onSettingsClick = onNavigateToSettings
            )
        },
        modifier = modifier.fillMaxSize(),
        containerColor = HomeBackgroundLight // Set the background color to light blue
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = HomeBackgroundLight // Set the surface color to match the background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it }
                )
                
                ToolsGrid(
                    tools = tools,
                    onToolClick = { tool -> onNavigateToTool(tool.route) }
                )
            }
        }
    }
}

/**
 * Grid of AI tools
 */
@Composable
private fun ToolsGrid(
    tools: List<AITool>,
    onToolClick: (AITool) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Change to 3 columns to match the image
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(tools) { tool ->
            ToolCard(
                tool = tool,
                onClick = { onToolClick(tool) }
            )
        }
    }
} 