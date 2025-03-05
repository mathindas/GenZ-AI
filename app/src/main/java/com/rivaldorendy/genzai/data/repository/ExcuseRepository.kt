package com.rivaldorendy.genzai.data.repository

import com.rivaldorendy.genzai.data.api.Content
import com.rivaldorendy.genzai.data.api.GeminiApiService
import com.rivaldorendy.genzai.data.api.GeminiRequest
import com.rivaldorendy.genzai.data.api.Part
import com.rivaldorendy.genzai.data.model.ExcuseForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for generating excuses using the Gemini AI API
 */
@Singleton
class ExcuseRepository @Inject constructor(
    private val apiService: GeminiApiService
) {
    
    /**
     * Generate an excuse based on the form data
     * 
     * @param form The form data containing excuse parameters
     * @param apiKey The Gemini AI API key
     * @param language The language to generate the excuse in ("en" or "id")
     * @return The generated excuse text or an error message
     */
    suspend fun generateExcuse(form: ExcuseForm, apiKey: String, language: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val languageText = if (language == "id") "Bahasa Indonesia" else "English"
            
            val prompt = """
                You are an expert assistant who helps users craft creative excuses for being late, sick, or requesting permission. Generate an excuse based on the following parameters:
                - Time of Day: ${form.timeOfDay}
                - Relationship: ${form.relationship}
                - Salutation: ${form.callTitle}
                - Reason Type: ${form.excuseType}
                - Reason Detail: ${form.excuseDetail}
                - Mood Level (1-10): ${form.moodLevel} (1 = serious & formal, 10 = very casual & funny)
                
                Create an excuse that fits the situation and make sure the excuse style matches the mood level, relationship type, and reason type. Dont seeming far-fetched, and as logical as possible. Don't make it too long, just about 4-6 sentences. Write the excuse in **$languageText**.
            """.trimIndent()
            
            val content = Content(parts = listOf(Part(text = prompt)))
            val request = GeminiRequest(contents = listOf(content))
            
            val response = apiService.generateText(apiKey, request)
            
            if (response.isSuccessful) {
                val candidate = response.body()?.candidates?.firstOrNull()
                if (candidate != null) {
                    val excuseText = candidate.content.parts.firstOrNull()?.text ?: 
                        "Sorry, couldn't generate an excuse. Please try again."
                    Result.success(excuseText)
                } else {
                    Result.failure(Exception("No response generated"))
                }
            } else {
                Result.failure(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 