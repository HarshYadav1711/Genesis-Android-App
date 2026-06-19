package com.genesis.showroom.ui.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.genesis.showroom.data.CustomerProfile
import com.genesis.showroom.ui.theme.GenesisBorder
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisFontFamily
import com.genesis.showroom.ui.theme.GenesisMuted
import com.genesis.showroom.ui.theme.GenesisPanel
import com.genesis.showroom.ui.theme.GenesisSilver
import com.genesis.showroom.ui.theme.GenesisTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomerProfilePanel(
    profile: CustomerProfile?,
    modifier: Modifier = Modifier,
) {
    if (profile == null) return

    val scoreColors = leadScoreColors(profile.leadScore)
    val intentLabel = intentLabel(profile.intent)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(GenesisPanel)
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisBorder,
                shape = RoundedCornerShape(8.dp),
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = GenesisTheme.spacing.borderWidthSubtle,
                    color = GenesisBorder,
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Customer Profile",
                fontFamily = GenesisFontFamily.Body,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                letterSpacing = 2.sp,
                color = GenesisCopper,
            )
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 50))
                    .background(scoreColors.background)
                    .border(
                        width = GenesisTheme.spacing.borderWidthSubtle,
                        color = scoreColors.border,
                        shape = RoundedCornerShape(percent = 50),
                    )
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(scoreColors.dot),
                )
                Text(
                    text = profile.leadScore.uppercase(),
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp,
                    color = scoreColors.text,
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ProfileField(
                label = "Intent",
                value = intentLabel.label,
                valueColor = intentLabel.color,
            )

            if (profile.vehiclePreferences.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ProfileLabel("Interested In")
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        profile.vehiclePreferences.forEach { vehicle ->
                            ProfileChip(text = vehicle, highlighted = false)
                        }
                    }
                }
            }

            if (profile.priorities.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ProfileLabel("Priorities")
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        profile.priorities.forEach { priority ->
                            ProfileChip(text = priority.replaceFirstChar { it.uppercase() }, highlighted = true)
                        }
                    }
                }
            }

            if (profile.budgetLevel.isNotBlank()) {
                ProfileField(
                    label = "Budget Range",
                    value = "${profile.budgetLevel.replaceFirstChar { it.uppercase() }} tier",
                    valueColor = GenesisSilver,
                )
            }

            if (profile.recommendedAction.isNotBlank()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = GenesisTheme.spacing.borderWidthSubtle,
                            color = GenesisBorder,
                        )
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    ProfileLabel("Suggested Action")
                    Text(
                        text = profile.recommendedAction.replace('_', ' ')
                            .replaceFirstChar { it.uppercase() },
                        fontFamily = GenesisFontFamily.Body,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = GenesisCopper,
                    )
                }
            }

            if (profile.conversationInsights.isNotBlank()) {
                Text(
                    text = "\"${profile.conversationInsights}\"",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(GenesisBlack)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = GenesisMuted,
                )
            }
        }
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String,
    valueColor: Color,
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ProfileLabel(label)
        Text(
            text = value,
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = valueColor,
        )
    }
}

@Composable
private fun ProfileLabel(text: String) {
    Text(
        text = text.uppercase(),
        fontFamily = GenesisFontFamily.Body,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp,
        color = GenesisMuted,
    )
}

@Composable
private fun ProfileChip(
    text: String,
    highlighted: Boolean,
) {
    Text(
        text = text,
        modifier = Modifier
            .padding(bottom = 6.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(
                if (highlighted) GenesisCopper.copy(alpha = 0.1f) else GenesisBlack,
            )
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = if (highlighted) GenesisCopper.copy(alpha = 0.2f) else GenesisBorder,
                shape = RoundedCornerShape(percent = 50),
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        fontFamily = GenesisFontFamily.Body,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        color = if (highlighted) GenesisCopper else GenesisSilver,
        textAlign = TextAlign.Center,
    )
}

private data class LeadScoreColors(
    val background: Color,
    val text: Color,
    val border: Color,
    val dot: Color,
)

private data class IntentLabel(
    val label: String,
    val color: Color,
)

private fun leadScoreColors(score: String): LeadScoreColors = when (score) {
    "warm" -> LeadScoreColors(
        background = Color(0xFF78350F).copy(alpha = 0.3f),
        text = Color(0xFFFCD34D),
        border = Color(0xFFB45309).copy(alpha = 0.4f),
        dot = Color(0xFFFBBF24),
    )
    "hot" -> LeadScoreColors(
        background = Color(0xFF7F1D1D).copy(alpha = 0.3f),
        text = Color(0xFFFCA5A5),
        border = Color(0xFFB91C1C).copy(alpha = 0.4f),
        dot = Color(0xFFF87171),
    )
    else -> LeadScoreColors(
        background = Color(0xFF1E3A8A).copy(alpha = 0.3f),
        text = Color(0xFF93C5FD),
        border = Color(0xFF1D4ED8).copy(alpha = 0.4f),
        dot = Color(0xFF60A5FA),
    )
}

private fun intentLabel(intent: String): IntentLabel = when (intent) {
    "comparing" -> IntentLabel("Comparing", Color(0xFF93C5FD))
    "high_interest" -> IntentLabel("High Interest", Color(0xFFFCD34D))
    "ready_to_buy" -> IntentLabel("Ready to Buy", Color(0xFF86EFAC))
    else -> IntentLabel("Browsing", GenesisMuted)
}
