package com.genesis.showroom.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.genesis.showroom.data.AppLanguage
import com.genesis.showroom.ui.language.GenesisLanguageProvider
import com.genesis.showroom.ui.language.LocalGenesisLanguage
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisBorder
import com.genesis.showroom.ui.theme.GenesisCharcoal
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisFontFamily
import com.genesis.showroom.ui.theme.GenesisMuted
import com.genesis.showroom.ui.theme.GenesisSilver
import com.genesis.showroom.ui.theme.GenesisTheme

private val CapsuleShape = RoundedCornerShape(50)
private val SegmentShape = RoundedCornerShape(50)

/**
 * EN / AR segmented capsule toggle — matches genesis-ai-demo `LanguageToggle.jsx`.
 *
 * Reads global language state; segment order mirrors under RTL layout direction.
 */
@Composable
fun LanguageToggle(
    modifier: Modifier = Modifier,
) {
    val languageState = LocalGenesisLanguage.current

    LanguageToggle(
        language = languageState.language,
        onLanguageChange = languageState::selectLanguage,
        modifier = modifier,
    )
}

@Composable
fun LanguageToggle(
    language: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(CapsuleShape)
            .background(GenesisCharcoal)
            .border(BorderStroke(1.dp, GenesisBorder), CapsuleShape)
            .padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LanguageSegment(
            flag = "🇬🇧",
            code = "EN",
            selected = language == AppLanguage.EN,
            onClick = { onLanguageChange(AppLanguage.EN) },
        )
        LanguageSegment(
            flag = "🇦🇪",
            code = "AR",
            selected = language == AppLanguage.AR,
            onClick = { onLanguageChange(AppLanguage.AR) },
        )
    }
}

@Composable
private fun LanguageSegment(
    flag: String,
    code: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "segmentScale",
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) GenesisCopper else Color.Transparent,
        animationSpec = tween(durationMillis = 200),
        label = "segmentBackground",
    )
    val contentColor by animateColorAsState(
        targetValue = when {
            selected -> GenesisBlack
            pressed -> GenesisSilver
            else -> GenesisMuted
        },
        animationSpec = tween(durationMillis = 200),
        label = "segmentContent",
    )

    Row(
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(SegmentShape)
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = flag,
            fontSize = 12.sp,
        )
        Text(
            text = code,
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            letterSpacing = 0.5.sp,
            color = contentColor,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A)
@Composable
private fun LanguageToggleEnPreview() {
    GenesisTheme {
        GenesisLanguageProvider {
            LanguageToggle()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A)
@Composable
private fun LanguageToggleArPreview() {
    GenesisTheme {
        LanguageToggle(
            language = AppLanguage.AR,
            onLanguageChange = {},
        )
    }
}
