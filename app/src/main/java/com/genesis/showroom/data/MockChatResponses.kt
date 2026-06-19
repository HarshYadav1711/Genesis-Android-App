package com.genesis.showroom.data

import android.content.Context
import kotlinx.serialization.json.Json

class VehicleRepository(context: Context) {
    private val json = Json { ignoreUnknownKeys = true }

    val vehicles: List<Vehicle> by lazy {
        val raw = context.assets.open("vehicles.json").bufferedReader().use { it.readText() }
        json.decodeFromString<List<Vehicle>>(raw)
    }

    fun filterVehicles(filter: VehicleFilter): List<Vehicle> =
        vehicles.filter { filter.matches(it) }
}

object MockChatResponses {
    fun greeting(language: AppLanguage, vehicle: Vehicle?): String = when {
        language.isArabic ->
            "مرحباً بك في Genesis. أنا مستشارك الشخصي. كيف يمكنني مساعدتك اليوم؟"
        vehicle != null ->
            "Welcome to Genesis. I see you're interested in the ${vehicle.name}. " +
                "I'd be happy to tell you more about it or help you find the perfect Genesis for you. " +
                "What would you like to know?"
        else ->
            "Welcome to Genesis. I'm your personal AI concierge. Whether you're looking for a powerful sedan, " +
                "a luxury SUV, or an electric vehicle — I'm here to guide you. What brings you in today?"
    }

    fun respond(language: AppLanguage, userMessage: String, vehicle: Vehicle?): String {
        val query = userMessage.lowercase()
        return when {
            language.isArabic && (query.contains("سعر") || query.contains("price")) ->
                vehicle?.let {
                    "يبدأ سعر ${it.name} من ${it.startingPrice}. للحصول على عروض حالية، يرجى زيارة صالة العرض."
                } ?: "تبدأ أسعار Genesis من AED 165,000. أخبرني عن الموديل الذي يهمك."

            query.contains("price") || query.contains("cost") || query.contains("msrp") ->
                vehicle?.let {
                    "The ${it.name} starts from ${it.startingPrice}. Contact our showroom for current offers and financing options."
                } ?: "Genesis models start from AED 165,000. Tell me which model interests you and I can share specific pricing."

            query.contains("electric") || query.contains("ev") || query.contains("كهرب") ->
                if (language.isArabic) {
                    "تقدم Genesis ثلاثة موديلات كهربائية: GV60، Electrified G80، و Electrified GV70. هل تود معرفة المزيد عن أحدهم؟"
                } else {
                    "Genesis offers three electric models: GV60, Electrified G80, and Electrified GV70. Would you like details on any of these?"
                }

            query.contains("test drive") || query.contains("تجربة") ->
                if (language.isArabic) {
                    "يسعدنا ترتيب تجربة قيادة. تواصل مع فريق صالة العرض في دبي أو أبوظبي أو الشارقة."
                } else {
                    "We'd be delighted to arrange a test drive. Visit our showroom team in Dubai, Abu Dhabi, or Sharjah to schedule yours."
                }

            vehicle != null ->
                if (language.isArabic) {
                    "${vehicle.name} — ${vehicle.tagline}. ${vehicle.highlights.take(2).joinToString("، ")}. ماذا تود أن تعرف أكثر؟"
                } else {
                    "The ${vehicle.name} — ${vehicle.tagline}. Highlights include ${vehicle.highlights.take(2).joinToString(", ")}. What would you like to explore further?"
                }

            else ->
                if (language.isArabic) {
                    "يسعدني مساعدتك في استكشاف مجموعة Genesis. اسأل عن الأداء، الأسعار، الموديلات الكهربائية، أو تجربة القيادة."
                } else {
                    "I'm here to help you explore the Genesis lineup. Ask about performance, pricing, electric models, or booking a test drive."
                }
        }
    }
}
