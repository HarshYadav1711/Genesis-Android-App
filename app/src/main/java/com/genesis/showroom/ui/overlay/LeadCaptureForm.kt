package com.genesis.showroom.ui.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.genesis.showroom.data.AppLanguage
import com.genesis.showroom.data.ChatRepository
import com.genesis.showroom.data.CustomerProfile
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisBorder
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisFontFamily
import com.genesis.showroom.ui.theme.GenesisMuted
import com.genesis.showroom.ui.theme.GenesisPanel
import com.genesis.showroom.ui.theme.GenesisSilver
import com.genesis.showroom.ui.theme.GenesisTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LeadCaptureForm(
    conversationId: String?,
    profile: CustomerProfile?,
    language: AppLanguage,
    chatRepository: ChatRepository,
    onComplete: (name: String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isArabic = language.isArabic
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var submitting by remember { mutableStateOf(false) }
    var submitted by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    fun submitLead() {
        if (name.isBlank() || email.isBlank()) {
            error = if (isArabic) {
                "الرجاء إدخال الاسم والبريد الإلكتروني"
            } else {
                "Please enter your name and email."
            }
            return
        }

        scope.launch {
            submitting = true
            error = ""

            val result = chatRepository.captureLead(
                name = name.trim(),
                email = email.trim(),
                phone = phone.trim().takeIf { it.isNotBlank() },
                conversationId = conversationId,
                profile = profile,
            )

            submitting = false
            if (result.isSuccess) {
                submitted = true
                delay(2000)
                onComplete(name.trim())
            } else {
                error = if (isArabic) {
                    "حدث خطأ. يرجى المحاولة مرة أخرى."
                } else {
                    "Something went wrong. Please try again."
                }
            }
        }
    }

    if (submitted) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(GenesisPanel)
                .border(
                    width = GenesisTheme.spacing.borderWidthSubtle,
                    color = GenesisCopper.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp),
                )
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(GenesisCopper.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = GenesisCopper,
                    modifier = Modifier.size(20.dp),
                )
            }
            Text(
                text = if (isArabic) "شكراً لك، $name" else "Thank you, $name",
                fontFamily = GenesisFontFamily.Body,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = GenesisSilver,
                textAlign = TextAlign.Center,
            )
            Text(
                text = if (isArabic) {
                    "سنتواصل معك قريباً بتوصيات مخصصة."
                } else {
                    "We'll reach out shortly with personalized recommendations."
                },
                fontFamily = GenesisFontFamily.Body,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = GenesisMuted,
                textAlign = TextAlign.Center,
            )
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(GenesisPanel)
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisCopper.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = if (isArabic) "احصل على معلومات مخصصة" else "Get Personalized Information",
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            letterSpacing = 2.sp,
            color = GenesisCopper,
        )

        LeadFormField(
            value = name,
            onValueChange = { name = it },
            placeholder = if (isArabic) "اسمك الكريم" else "Your Name",
        )
        LeadFormField(
            value = email,
            onValueChange = { email = it },
            placeholder = if (isArabic) "بريدك الإلكتروني" else "Email Address",
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "+971",
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(GenesisBlack)
                    .border(
                        width = GenesisTheme.spacing.borderWidthSubtle,
                        color = GenesisBorder,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                fontFamily = GenesisFontFamily.Body,
                fontSize = 14.sp,
                color = GenesisMuted,
            )
            LeadFormField(
                value = phone,
                onValueChange = { phone = it },
                placeholder = if (isArabic) "رقم الهاتف (اختياري)" else "Phone (optional)",
                modifier = Modifier.weight(1f),
            )
        }

        if (error.isNotBlank()) {
            Text(
                text = error,
                fontFamily = GenesisFontFamily.Body,
                fontSize = 12.sp,
                color = Color(0xFFF87171),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = when {
                    submitting && isArabic -> "جاري الإرسال..."
                    submitting -> "Sending..."
                    isArabic -> "إرسال"
                    else -> "Send"
                },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (submitting) GenesisCopper.copy(alpha = 0.6f) else GenesisCopper)
                    .clickable(enabled = !submitting) { submitLead() }
                    .padding(vertical = 12.dp),
                fontFamily = GenesisFontFamily.Body,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                letterSpacing = 1.sp,
                color = GenesisBlack,
                textAlign = TextAlign.Center,
            )
            Text(
                text = if (isArabic) "لاحقاً" else "Later",
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .border(
                        width = GenesisTheme.spacing.borderWidthSubtle,
                        color = GenesisBorder,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .clickable(enabled = !submitting) { onComplete(null) }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                fontFamily = GenesisFontFamily.Body,
                fontSize = 14.sp,
                color = GenesisMuted,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun LeadFormField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(GenesisBlack)
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisBorder,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(horizontal = 12.dp, vertical = 12.dp),
        textStyle = androidx.compose.ui.text.TextStyle(
            fontFamily = GenesisFontFamily.Body,
            fontSize = 14.sp,
            color = GenesisSilver,
        ),
        cursorBrush = SolidColor(GenesisCopper),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontFamily = GenesisFontFamily.Body,
                        fontSize = 14.sp,
                        color = GenesisMuted,
                    )
                }
                innerTextField()
            }
        },
    )
}
