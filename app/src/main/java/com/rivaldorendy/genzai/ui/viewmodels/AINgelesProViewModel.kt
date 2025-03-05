package com.rivaldorendy.genzai.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.rivaldorendy.genzai.data.model.ExcuseForm
import com.rivaldorendy.genzai.data.repository.ExcuseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AINgelesProViewModel @Inject constructor(
    private val excuseRepository: ExcuseRepository
) : ViewModel() {
    
    suspend fun generateExcuse(form: ExcuseForm, apiKey: String, language: String): Result<String> {
        return excuseRepository.generateExcuse(form, apiKey, language)
    }
} 