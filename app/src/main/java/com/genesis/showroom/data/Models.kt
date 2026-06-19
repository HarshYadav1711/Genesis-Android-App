package com.genesis.showroom.data

import kotlinx.serialization.Serializable

@Serializable
data class Vehicle(
    val id: String,
    val name: String,
    val type: String,
    val tagline: String,
    val image: String,
    val startingPrice: String,
    val badge: String? = null,
    val isElectric: Boolean = false,
    val specs: Map<String, Map<String, String>> = emptyMap(),
    val highlights: List<String> = emptyList(),
    val competitors: List<String> = emptyList(),
    val bestFor: String? = null,
)

fun Vehicle.specEntries(tabId: String): List<Pair<String, String>> {
    val tabMap = specs[tabId] ?: return emptyList()
    return tabMap.entries.map { it.key to it.value }
}

enum class AppLanguage(val code: String) {
    EN("en"),
    AR("ar"),
    ;

    val isArabic: Boolean get() = this == AR
}

enum class AppScreen {
    WELCOME,
    VEHICLES,
    SPEC_BOARD,
}

enum class VehicleFilter(val id: String) {
    ALL("all"),
    SEDAN("sedan"),
    SUV("suv"),
    ELECTRIC("electric"),
    ;

    fun label(language: AppLanguage): String = when (this) {
        ALL -> if (language.isArabic) "جميع الموديلات" else "All Models"
        SEDAN -> if (language.isArabic) "سيدان" else "Sedans"
        SUV -> if (language.isArabic) "دفع رباعي" else "SUVs"
        ELECTRIC -> if (language.isArabic) "كهربائي" else "Electric"
    }
}

fun VehicleFilter.matches(vehicle: Vehicle): Boolean = when (this) {
    VehicleFilter.ALL -> true
    VehicleFilter.ELECTRIC -> vehicle.isElectric
    VehicleFilter.SEDAN -> vehicle.type.contains("sedan", ignoreCase = true)
    VehicleFilter.SUV -> vehicle.type.contains("suv", ignoreCase = true)
}

enum class SpecTab(val id: String, val icon: String) {
    PERFORMANCE("performance", "⚡"),
    DIMENSIONS("dimensions", "📐"),
    INTERIOR("interior", "🪑"),
    SAFETY("safety", "🛡"),
    WHEELS("wheels", "⚙️"),
    PRICING("pricing", "💎"),
    ;

    fun label(language: AppLanguage): String = when (this) {
        PERFORMANCE -> if (language.isArabic) "الأداء" else "Performance"
        DIMENSIONS -> if (language.isArabic) "الأبعاد" else "Dimensions"
        INTERIOR -> if (language.isArabic) "المقصورة" else "Interior"
        SAFETY -> if (language.isArabic) "السلامة" else "Safety"
        WHEELS -> if (language.isArabic) "العجلات" else "Wheels"
        PRICING -> if (language.isArabic) "الأسعار" else "Pricing"
    }
}

enum class VoiceState {
    IDLE,
    LISTENING,
    THINKING,
    SPEAKING,
}

data class ChatMessage(
    val role: ChatRole,
    val content: String,
    val isError: Boolean = false,
)

enum class ChatRole {
    USER,
    ASSISTANT,
}
