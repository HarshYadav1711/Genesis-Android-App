package com.genesis.showroom.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiMessage(
    val role: String,
    val content: String,
)

@Serializable
data class ChatRequest(
    val message: String,
    @SerialName("conversation_id") val conversationId: String? = null,
    @SerialName("conversation_history") val conversationHistory: List<ApiMessage> = emptyList(),
    @SerialName("current_vehicle") val currentVehicle: String? = null,
    val language: String = "en",
)

@Serializable
data class ChatResponse(
    val response: String,
    @SerialName("conversation_id") val conversationId: String,
    @SerialName("profile_update") val profileUpdate: JsonElement? = null,
    @SerialName("current_profile") val currentProfile: CustomerProfileDto,
)

@Serializable
data class CustomerProfileDto(
    val intent: String = "browsing",
    @SerialName("vehicle_preferences") val vehiclePreferences: List<String> = emptyList(),
    val priorities: List<String> = emptyList(),
    @SerialName("budget_level") val budgetLevel: String = "mid",
    @SerialName("recommended_action") val recommendedAction: String = "engage",
    @SerialName("lead_score") val leadScore: String = "cold",
    @SerialName("should_capture_lead") val shouldCaptureLead: Boolean = false,
    @SerialName("conversation_insights") val conversationInsights: String = "",
    @SerialName("message_count") val messageCount: Int = 0,
    @SerialName("lead_captured") val leadCaptured: Boolean = false,
)

@Serializable
data class LeadRequest(
    val name: String,
    val email: String,
    val phone: String? = null,
    @SerialName("conversation_id") val conversationId: String? = null,
    @SerialName("vehicle_interest") val vehicleInterest: String? = null,
    @SerialName("intent_score") val intentScore: String = "warm",
    val priorities: List<String> = emptyList(),
    @SerialName("budget_level") val budgetLevel: String? = null,
)

@Serializable
data class LeadResponse(
    val success: Boolean,
    @SerialName("lead_id") val leadId: Int? = null,
    val message: String? = null,
)

@Serializable
data class ApiErrorDetail(
    val detail: String? = null,
)
