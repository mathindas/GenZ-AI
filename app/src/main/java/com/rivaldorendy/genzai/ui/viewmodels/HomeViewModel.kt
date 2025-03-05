package com.rivaldorendy.genzai.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.rivaldorendy.genzai.data.model.AITool
import com.rivaldorendy.genzai.data.repository.ToolsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val toolsRepository: ToolsRepository
) : ViewModel() {
    
    fun searchTools(query: String): List<AITool> {
        return toolsRepository.searchTools(query)
    }
} 