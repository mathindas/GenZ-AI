package com.rivaldorendy.genzai.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Retrofit service interface for Gemini AI API
 */
interface GeminiApiService {
    
    @POST("models/gemini-2.0-flash:generateContent")
    suspend fun generateText(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>
}

/**
 * Data classes for Gemini API request
 */
data class GeminiRequest(
    val contents: List<Content>
)

data class Content(
    val parts: List<Part>
)

data class Part(
    val text: String
)

/**
 * Data classes for Gemini API response
 */
data class GeminiResponse(
    val candidates: List<Candidate>
)

data class Candidate(
    val content: Content,
    val finishReason: String
) 