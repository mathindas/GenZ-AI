package com.rivaldorendy.genzai.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rivaldorendy.genzai.R
import com.rivaldorendy.genzai.data.model.AppSettings
import com.rivaldorendy.genzai.ui.components.AppBar

/**
 * Settings screen for configuring app preferences
 */
@Composable
fun SettingsScreen(
    settings: AppSettings,
    onSaveSettings: (AppSettings) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var language by remember { mutableStateOf(settings.language) }
    var isDarkMode by remember { mutableStateOf(settings.isDarkMode) }
    var apiKey by remember { mutableStateOf(settings.apiKey) }
    
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.settings_title),
                showBackButton = true,
                onBackClick = onNavigateBack
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingsCard {
                    LanguageSetting(
                        selectedLanguage = language,
                        onLanguageSelected = { language = it }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    ThemeSetting(
                        isDarkMode = isDarkMode,
                        onThemeChanged = { isDarkMode = it }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    ApiKeySetting(
                        apiKey = apiKey,
                        onApiKeyChanged = { apiKey = it }
                    )
                }
                
                Button(
                    onClick = {
                        onSaveSettings(
                            AppSettings(
                                language = language,
                                isDarkMode = isDarkMode,
                                apiKey = apiKey
                            )
                        )
                        onNavigateBack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.settings_save))
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                TextButton(
                    onClick = onNavigateToAbout,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(R.string.menu_about))
                }
            }
        }
    }
}

@Composable
private fun SettingsCard(
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSetting(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.settings_language),
            style = MaterialTheme.typography.titleMedium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = if (selectedLanguage == "en") 
                    stringResource(R.string.language_english) 
                else 
                    stringResource(R.string.language_indonesian),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.language_english)) },
                    onClick = {
                        onLanguageSelected("en")
                        expanded = false
                    }
                )
                
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.language_indonesian)) },
                    onClick = {
                        onLanguageSelected("id")
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun ThemeSetting(
    isDarkMode: Boolean,
    onThemeChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.settings_theme),
            style = MaterialTheme.typography.titleMedium
        )
        
        Switch(
            checked = isDarkMode,
            onCheckedChange = onThemeChanged
        )
    }
}

@Composable
private fun ApiKeySetting(
    apiKey: String,
    onApiKeyChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.settings_api_key),
            style = MaterialTheme.typography.titleMedium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = apiKey,
            onValueChange = onApiKeyChanged,
            placeholder = { Text(stringResource(R.string.settings_api_key_hint)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
} 