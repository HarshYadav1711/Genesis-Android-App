package com.genesis.showroom.data

import com.genesis.showroom.data.api.ApiMessage
import com.genesis.showroom.data.api.ChatRequest
import com.genesis.showroom.data.api.CustomerProfileDto
import com.genesis.showroom.data.api.GenesisApiException
import com.genesis.showroom.data.api.GenesisApiService
import com.genesis.showroom.data.api.LeadRequest

class ChatRepository(
    private val apiService: GenesisApiService,
) {
    suspend fun sendMessage(
        text: String,
        conversationId: String?,
        conversationHistory: List<ApiMessage>,
        currentVehicleId: String?,
        language: AppLanguage,
    ): Result<ChatSendResult> {
        if (text.isBlank()) return Result.failure(IllegalArgumentException("Empty message"))

        return try {
            val response = apiService.sendChat(
                ChatRequest(
                    message = text,
                    conversationId = conversationId,
                    conversationHistory = conversationHistory.takeLast(10),
                    currentVehicle = currentVehicleId,
                    language = language.code,
                ),
            )

            Result.success(
                ChatSendResult(
                    responseText = response.response,
                    conversationId = response.conversationId,
                    profile = response.currentProfile.toDomain(),
                ),
            )
        } catch (error: GenesisApiException) {
            Result.failure(error)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    suspend fun captureLead(
        name: String,
        email: String,
        phone: String?,
        conversationId: String?,
        profile: CustomerProfile?,
    ): Result<Unit> {
        return try {
            apiService.captureLead(
                LeadRequest(
                    name = name,
                    email = email,
                    phone = phone?.takeIf { it.isNotBlank() },
                    conversationId = conversationId,
                    vehicleInterest = profile?.vehiclePreferences?.joinToString(", ")?.takeIf { it.isNotBlank() },
                    intentScore = profile?.leadScore ?: "warm",
                    priorities = profile?.priorities.orEmpty(),
                    budgetLevel = profile?.budgetLevel,
                ),
            )
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}

data class ChatSendResult(
    val responseText: String,
    val conversationId: String,
    val profile: CustomerProfile,
)

private fun CustomerProfileDto.toDomain(): CustomerProfile = CustomerProfile(
    intent = intent,
    vehiclePreferences = vehiclePreferences,
    priorities = priorities,
    budgetLevel = budgetLevel,
    recommendedAction = recommendedAction,
    leadScore = leadScore,
    shouldCaptureLead = shouldCaptureLead,
    conversationInsights = conversationInsights,
    messageCount = messageCount,
    leadCaptured = leadCaptured,
)
