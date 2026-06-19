package com.genesis.showroom.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

private val LabelLetterSpacing = 0.15.em

val GenesisTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = GenesisFontFamily.Display,
        fontWeight = GenesisFontFamily.DisplayLight,
        fontSize = 48.sp,
        lineHeight = 56.sp,
        letterSpacing = (-0.02).em,
    ),
    displayMedium = TextStyle(
        fontFamily = GenesisFontFamily.Display,
        fontWeight = GenesisFontFamily.DisplaySemiBold,
        fontSize = 40.sp,
        lineHeight = 48.sp,
        letterSpacing = (-0.01).em,
    ),
    displaySmall = TextStyle(
        fontFamily = GenesisFontFamily.Display,
        fontWeight = GenesisFontFamily.DisplaySemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = GenesisFontFamily.Display,
        fontWeight = GenesisFontFamily.DisplaySemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = GenesisFontFamily.Display,
        fontWeight = GenesisFontFamily.DisplayBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = GenesisFontFamily.Display,
        fontWeight = GenesisFontFamily.DisplaySemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = GenesisFontFamily.Body,
        fontWeight = GenesisFontFamily.BodySemiBold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.01.em,
    ),
    titleMedium = TextStyle(
        fontFamily = GenesisFontFamily.Body,
        fontWeight = GenesisFontFamily.BodyMedium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.01.em,
    ),
    titleSmall = TextStyle(
        fontFamily = GenesisFontFamily.Body,
        fontWeight = GenesisFontFamily.BodyMedium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.01.em,
    ),
    bodyLarge = TextStyle(
        fontFamily = GenesisFontFamily.Body,
        fontWeight = GenesisFontFamily.BodyRegular,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.02.em,
    ),
    bodyMedium = TextStyle(
        fontFamily = GenesisFontFamily.Body,
        fontWeight = GenesisFontFamily.BodyRegular,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.02.em,
    ),
    bodySmall = TextStyle(
        fontFamily = GenesisFontFamily.Body,
        fontWeight = GenesisFontFamily.BodyRegular,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.03.em,
    ),
    labelLarge = TextStyle(
        fontFamily = GenesisFontFamily.Body,
        fontWeight = GenesisFontFamily.BodySemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = LabelLetterSpacing,
    ),
    labelMedium = TextStyle(
        fontFamily = GenesisFontFamily.Body,
        fontWeight = GenesisFontFamily.BodyMedium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = LabelLetterSpacing,
    ),
    labelSmall = TextStyle(
        fontFamily = GenesisFontFamily.Body,
        fontWeight = GenesisFontFamily.BodyMedium,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = LabelLetterSpacing,
    ),
)

/** Editorial accent style for hero copy and copper-gradient treatments. */
val GenesisDisplayAccent = TextStyle(
    fontFamily = GenesisFontFamily.Display,
    fontWeight = GenesisFontFamily.DisplaySemiBold,
    fontStyle = GenesisFontFamily.Italic,
    fontSize = 32.sp,
    lineHeight = 40.sp,
)

/** Uppercase CTA label style used across Genesis digital properties. */
val GenesisButtonLabel = TextStyle(
    fontFamily = GenesisFontFamily.Body,
    fontWeight = GenesisFontFamily.BodySemiBold,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.25.em,
)

/** Fine-print and legal copy. */
val GenesisCaption = TextStyle(
    fontFamily = GenesisFontFamily.Body,
    fontWeight = GenesisFontFamily.BodyRegular,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.04.em,
)

/** Underlined navigation link style. */
val GenesisLink = TextStyle(
    fontFamily = GenesisFontFamily.Body,
    fontWeight = GenesisFontFamily.BodyMedium,
    fontSize = 13.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.08.em,
    textDecoration = TextDecoration.Underline,
)
