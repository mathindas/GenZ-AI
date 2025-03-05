package com.rivaldorendy.genzai.data.model

/**
 * Data class representing the form data for generating an excuse
 * 
 * @property timeOfDay Time of day (e.g., "Pagi", "Siang", "Sore", "Malam")
 * @property relationship Relationship type (e.g., "Atasan", "Guru", "Teman", "Sahabat")
 * @property callTitle Title or salutation (e.g., "Pak", "Bu", "Mbak")
 * @property excuseType Type of excuse (e.g., "Sakit", "Telat", "Izin")
 * @property excuseDetail Detailed reason for the excuse
 * @property moodLevel Formality level (1-10, where 1 = formal, 10 = casual)
 */
data class ExcuseForm(
    val timeOfDay: String = "",
    val relationship: String = "",
    val callTitle: String = "",
    val excuseType: String = "",
    val excuseDetail: String = "",
    val moodLevel: Int = 5
) 