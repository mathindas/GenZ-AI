package com.rivaldorendy.genzai.ui.screens.aingeles

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rivaldorendy.genzai.R
import com.rivaldorendy.genzai.data.model.AppSettings
import com.rivaldorendy.genzai.data.model.ExcuseForm
import com.rivaldorendy.genzai.ui.components.AppBar
import com.rivaldorendy.genzai.ui.viewmodels.AINgelesProViewModel
import kotlinx.coroutines.launch

/**
 * AI Ngeles Pro screen for generating creative excuses
 */
@Composable
fun AINgelesProScreen(
    settings: AppSettings,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AINgelesProViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()
    
    // State for expanding/collapsing sections
    var isBasicInfoExpanded by remember { mutableStateOf(true) }
    var isDetailsExpanded by remember { mutableStateOf(true) }
    
    // Form fields state
    var timeOfDay by remember { mutableStateOf("") }
    var relationship by remember { mutableStateOf("") }
    var callTitle by remember { mutableStateOf("") }
    var excuseType by remember { mutableStateOf("") }
    var excuseDetail by remember { mutableStateOf("") }
    var moodLevel by remember { mutableIntStateOf(5) }
    
    var generatedExcuse by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    // Get localized options based on language setting
    val timeOptions = remember(settings.language) {
        if (settings.language == "id") {
            listOf("Pagi", "Siang", "Sore", "Malam")
        } else {
            listOf("Morning", "Afternoon", "Evening", "Night")
        }
    }
    
    val relationshipOptions = remember(settings.language) {
        if (settings.language == "id") {
            listOf("Atasan", "Guru", "Teman", "Sahabat")
        } else {
            listOf("Boss", "Teacher", "Friend", "Best Friend")
        }
    }
    
    val excuseTypeOptions = remember(settings.language) {
        if (settings.language == "id") {
            listOf("Sakit", "Telat", "Izin")
        } else {
            listOf("Sick", "Late", "Permission")
        }
    }
    
    // Initialize with first option if empty
    LaunchedEffect(timeOptions) {
        if (timeOfDay.isEmpty()) timeOfDay = timeOptions.first()
        if (relationship.isEmpty()) relationship = relationshipOptions.first()
        if (excuseType.isEmpty()) excuseType = excuseTypeOptions.first()
    }
    
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.ai_ngeles_pro_title),
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
                // Basic Info Section (Collapsible)
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Section Header with expand/collapse button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Basic Information",
                                style = MaterialTheme.typography.titleLarge
                            )
                            IconButton(onClick = { isBasicInfoExpanded = !isBasicInfoExpanded }) {
                                Icon(
                                    imageVector = if (isBasicInfoExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = if (isBasicInfoExpanded) "Collapse" else "Expand"
                                )
                            }
                        }
                        
                        AnimatedVisibility(visible = isBasicInfoExpanded) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Time of Day and Relationship in a row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // Time of Day
                                    DropdownField(
                                        label = stringResource(R.string.time_of_day),
                                        selectedOption = timeOfDay,
                                        options = timeOptions,
                                        onOptionSelected = { timeOfDay = it },
                                        modifier = Modifier.weight(1f)
                                    )
                                    
                                    // Relationship
                                    DropdownField(
                                        label = stringResource(R.string.relationship),
                                        selectedOption = relationship,
                                        options = relationshipOptions,
                                        onOptionSelected = { relationship = it },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                
                                // Call Title
                                OutlinedTextField(
                                    value = callTitle,
                                    onValueChange = { callTitle = it },
                                    label = { Text(stringResource(R.string.call_title)) },
                                    placeholder = { Text(stringResource(R.string.call_title_hint)) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                
                                // Excuse Type
                                DropdownField(
                                    label = stringResource(R.string.excuse_type),
                                    selectedOption = excuseType,
                                    options = excuseTypeOptions,
                                    onOptionSelected = { excuseType = it }
                                )
                            }
                        }
                    }
                }
                
                // Details Section (Collapsible)
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Section Header with expand/collapse button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Excuse Details",
                                style = MaterialTheme.typography.titleLarge
                            )
                            IconButton(onClick = { isDetailsExpanded = !isDetailsExpanded }) {
                                Icon(
                                    imageVector = if (isDetailsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = if (isDetailsExpanded) "Collapse" else "Expand"
                                )
                            }
                        }
                        
                        AnimatedVisibility(visible = isDetailsExpanded) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Excuse Detail
                                OutlinedTextField(
                                    value = excuseDetail,
                                    onValueChange = { excuseDetail = it },
                                    label = { Text(stringResource(R.string.excuse_reason_detail)) },
                                    placeholder = { Text(stringResource(R.string.excuse_reason_detail_hint)) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                
                                // Mood Level
                                Text(
                                    text = stringResource(R.string.mood_level),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                
                                Slider(
                                    value = moodLevel.toFloat(),
                                    onValueChange = { moodLevel = it.toInt() },
                                    valueRange = 1f..10f,
                                    steps = 8,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(R.string.mood_level_formal),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                        text = stringResource(R.string.mood_level_casual),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
                
                // Generate Button
                Button(
                    onClick = {
                        if (callTitle.isBlank() || excuseDetail.isBlank()) {
                            errorMessage = "Please fill in all fields"
                            return@Button
                        }
                        
                        val apiKey = settings.apiKey.ifBlank { "YOUR_API_KEY" } // Replace with your default key
                        
                        isLoading = true
                        errorMessage = ""
                        
                        val form = ExcuseForm(
                            timeOfDay = timeOfDay,
                            relationship = relationship,
                            callTitle = callTitle,
                            excuseType = excuseType,
                            excuseDetail = excuseDetail,
                            moodLevel = moodLevel
                        )
                        
                        coroutineScope.launch {
                            viewModel.generateExcuse(form, apiKey, settings.language)
                                .onSuccess { result ->
                                    generatedExcuse = result
                                }
                                .onFailure { error ->
                                    errorMessage = error.message ?: "An error occurred"
                                }
                            
                            isLoading = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = stringResource(R.string.generate_excuse))
                }
                
                // Error message
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Result Card
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator()
                            Text(
                                text = stringResource(R.string.loading),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        } else if (generatedExcuse.isNotEmpty()) {
                            Text(
                                text = generatedExcuse,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                // Copy Button
                                TextButton(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(generatedExcuse))
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.copied_to_clipboard),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                    Text(text = stringResource(R.string.copy_text))
                                }
                                
                                // Share Button
                                TextButton(
                                    onClick = {
                                        val sendIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_TEXT, generatedExcuse)
                                            type = "text/plain"
                                        }
                                        
                                        val shareIntent = Intent.createChooser(sendIntent, null)
                                        context.startActivity(shareIntent)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "Share",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                    Text(text = stringResource(R.string.share))
                                }
                            }
                        } else {
                            Text(
                                text = stringResource(R.string.result_hint),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownField(
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { 
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(12.dp)
            )
            
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
} 