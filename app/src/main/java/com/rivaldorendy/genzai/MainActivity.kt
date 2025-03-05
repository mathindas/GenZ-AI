package com.rivaldorendy.genzai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.rivaldorendy.genzai.data.model.AppSettings
import com.rivaldorendy.genzai.data.repository.SettingsRepository
import com.rivaldorendy.genzai.ui.navigation.AppNavigation
import com.rivaldorendy.genzai.ui.theme.GenZAITheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var settingsRepository: SettingsRepository
    
    private val settingsFlow = MutableStateFlow(AppSettings())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Load settings
        lifecycleScope.launch {
            val settings = settingsRepository.appSettings.first()
            settingsFlow.value = settings
        }
        
        setContent {
            val settings by settingsFlow.collectAsState()
            
            GenZAITheme(darkTheme = settings.isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    AppNavigation(
                        navController = navController,
                        settingsFlow = settingsFlow,
                        onSaveSettings = { newSettings ->
                            lifecycleScope.launch {
                                settingsRepository.saveSettings(newSettings)
                                settingsFlow.value = newSettings
                            }
                        }
                    )
                }
            }
        }
    }
}