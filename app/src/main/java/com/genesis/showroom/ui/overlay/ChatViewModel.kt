package com.genesis.showroom.ui.overlay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.genesis.showroom.data.AppLanguage
import com.genesis.showroom.data.ChatMessage
import com.genesis.showroom.data.ChatRepository
import com.genesis.showroom.data.ChatRole
import com.genesis.showroom.data.CustomerProfile
import com.genesis.showroom.data.SendMessageResult
import com.genesis.showroom.data.Vehicle
import com.genesis.showroom.data.api.ApiMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatRepository: ChatRepository,
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _profile = MutableStateFlow<CustomerProfile?>(null)
    val profile: StateFlow<CustomerProfile?> = _profile.asStateFlow()

    private val _conversationId = MutableStateFlow<String?>(null)
    val conversationId: StateFlow<String?> = _conversationId.asStateFlow()

    private val conversationHistory = mutableListOf<ApiMessage>()

    fun sendMessage(
        text: String,
        language: AppLanguage,
        currentVehicle: Vehicle?,
    ) {
        val trimmed = text.trim()
        if (trimmed.isBlank() || _isLoading.value) return

        viewModelScope.launch {
            sendMessageAndAwait(trimmed, language, currentVehicle)
        }
    }

    suspend fun sendMessageAndAwait(
        text: String,
        language: AppLanguage,
        currentVehicle: Vehicle?,
    ): SendMessageResult? {
        val trimmed = text.trim()
        if (trimmed.isBlank() || _isLoading.value) return null

        val userMessage = ChatMessage(role = ChatRole.USER, content = trimmed)
        _messages.update { it + userMessage }
        conversationHistory.add(ApiMessage(role = "user", content = trimmed))

        _isLoading.value = true

        val result = chatRepository.sendMessage(
            text = trimmed,
            conversationId = _conversationId.value,
            conversationHistory = conversationHistory.toList(),
            currentVehicleId = currentVehicle?.id,
            language = language,
        )

        return result.fold(
            onSuccess = { chatResult ->
                if (_conversationId.value == null) {
                    _conversationId.value = chatResult.conversationId
                }
                _profile.value = chatResult.profile

                val assistantMessage = ChatMessage(
                    role = ChatRole.ASSISTANT,
                    content = chatResult.responseText,
                )
                _messages.update { it + assistantMessage }
                conversationHistory.add(ApiMessage(role = "assistant", content = chatResult.responseText))

                SendMessageResult(
                    text = chatResult.responseText,
                    profile = chatResult.profile,
                    shouldCaptureLead = chatResult.profile.shouldCaptureLead,
                )
            },
            onFailure = {
                val errorMessage = ChatMessage(
                    role = ChatRole.ASSISTANT,
                    content = if (language.isArabic) {
                        "عذراً، حدث خطأ. يرجى المحاولة مرة أخرى."
                    } else {
                        "I apologize — I'm having trouble connecting right now. Please try again in a moment."
                    },
                    isError = true,
                )
                _messages.update { it + errorMessage }
                null
            },
        ).also {
            _isLoading.value = false
        }
    }

    fun reset() {
        _messages.value = emptyList()
        _profile.value = null
        _conversationId.value = null
        _isLoading.value = false
        conversationHistory.clear()
    }
}

class ChatViewModelFactory(
    private val chatRepository: ChatRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(chatRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
